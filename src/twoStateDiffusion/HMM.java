package twoStateDiffusion;


import data.Trajectory;
import ij.IJ;


public class HMM 
{
	MCMC mcmc;
	public HMM()
	{
		
	}
	public void segment(Trajectory traj, double[] model)
	{
		double tau = traj.getMetrics().getFrameStep();
		double d1 = model[0];
		double d2 = model[1];
		double p12 = model[2];
		double p21 = model[3];
		double[] sq_dis = traj.getSqDis();
//		double[] sq_dis = traj.getSqDispPro();
		int n = sq_dis.length;
		
		//Calculate logs of stationary probabilities and the 
		//transition matrix for the given transition probabilities.
		double[][] logTrans = new double[][]{{Math.log(1 - p12), Math.log(p12)},
											 {Math.log(p21), Math.log(1 - p21)}};
		
		double[][] logTransPrime = new double[][]{{Math.log(1 - p12),Math.log(p12)},
												  {Math.log(p21), Math.log(1 - p21)}};
		
	    double logstat1 = Math.log(p21/(p12 + p21));
	    double logstat2 = Math.log(p12/(p12 + p21));
	    
	    //Calculate logLikelihood of d1 and d2 for the observed square displacements.
	    double d1Tau = d1*tau;
	    double d2Tau = d2*tau;
	    double fourD1Tau = 4*d1Tau;
	    double fourD2Tau = 4*d2Tau;
	    double[] lld1 = new double[n];
	    double[] lld2 = new double[n];
	    for(int i=0;i<n;i++)
	    {
	    	lld1[i] = -sq_dis[i]/fourD1Tau - Math.log(d1Tau);
	    	lld2[i] = -sq_dis[i]/fourD2Tau - Math.log(d2Tau);
	    }
	    
	    //Recursively calculate log of the forward variable alpha
	    double[][] logalpha = new double[n][2];
	    logalpha[0] = new double[]{logstat1+lld1[0],logstat2+lld2[0]};
	    for(int i=1;i<n;i++)
	    {
	    	logalpha[i][0] = lld1[i]+logsum(logalpha[i-1][0]+logTrans[0][0],logalpha[i-1][1]+logTrans[1][0]);
	    	logalpha[i][1] = lld2[i]+logsum(logalpha[i-1][0]+logTrans[0][1],logalpha[i-1][1]+logTrans[1][1]);
	    }
	    //Recursively calculate log of the backward variable beta
	    double[][] logbeta = new double[n][2];
	    for(int i=n-2;i>=0;i--)
	    {
	    	logbeta[i][0] = logsum(logTransPrime[0][0]+lld1[i+1]+logbeta[i+1][0],
	    						   logTransPrime[1][0]+lld2[i+1]+logbeta[i+1][1]);
	    	logbeta[i][1] = logsum(logTransPrime[0][1]+lld1[i+1]+logbeta[i+1][0],
					   			   logTransPrime[1][1]+lld2[i+1]+logbeta[i+1][1]);
	    }
	    double[][] loggamma = new double[n][2];
	    int[] bestStageGuess = new int[n];
	    int good = 0;
	    for(int i=0;i<n;i++)
	    {
	    	loggamma[i][0] = logalpha[i][0] + logbeta[i][0];
	    	loggamma[i][1] = logalpha[i][1] + logbeta[i][1];
    		bestStageGuess[i] = (loggamma[i][1]>loggamma[i][0]) ? 2:1;
    		
    		/*System.out.println("r:"+i+" lld1:"+lld1[i]+" llld2:"+lld2[i]);
    		System.out.println("r:"+i+" la0:"+logalpha[i][0]+" la1:"+logalpha[i][1]);
    		System.out.println("r:"+i+" lb0:"+logbeta[i][0]+" lb1:"+logbeta[i][1]);
    		System.out.println("r:"+i+" lg0:"+loggamma[i][0]+" lg1:"+loggamma[i][1]+" eS:"+bestStageGuess[i]+" rS:"+traj.state[i]);
    		System.out.println();
    		if(bestStageGuess[i]==traj.state[i])
    		{
    			good++;
    		}*/
	    }
	    //System.out.println("Coincidence: "+(100*good/n)+"%");
	    traj.setGState(bestStageGuess);
		//return bestStageGuess;
	}
	public void segment(final GUI gui_hmm, final Trajectory[] traj, final double[] model)
	{
		Runnable run = new Runnable()
		{
			@Override
			public void run() {
				for(int i=0;i<traj.length;i++)
				{
					segment(traj[i],model);
					gui_hmm.jProgressBar_HMM_MCMC_setValue((i+1)/traj.length);
				}
			}
		};
		Thread hilo = new Thread(run);
		hilo.start();
	}
	
	//Dodo's HMM2stateEnsembleMCMC_v1.m
	//public void MCMC(final GUIspt spt_gui, final HMMTraj[] trajs, final double delta_t, final double[] initial, final int steps)
	public void MCMC(final Trajectory[] trajs, final GUI gui_hmm, final double delta_t, final double[] initial, final int steps, double[] scale, int updatePlotsFrequency)
	{
		mcmc = new MCMC(trajs, gui_hmm, delta_t, initial, steps, scale, updatePlotsFrequency);  	
		mcmc.start();
	} 
	public void pauseMCMC()
	{
		mcmc.pause = true;
		IJ.log("pause:"+mcmc.pause);
	}
	public void continueMCMC()
	{
		mcmc.pause = false;
		IJ.log("pause:"+mcmc.pause);
	}
	public void stopMCMC()
	{
		mcmc.pause = false;
		mcmc.finish = true;
	}
	
	//Dodo's HMMensembleLogL_v2 mod
	public static double logLike(Trajectory[] trajs, double deltaT, double d1, double d2, double p12, double p21)
	{
		double[] sq_dis = trajs[0].getSqDis();
		int no_of_steps = sq_dis.length;
		
	    double[] logalpha1 = new double[no_of_steps];
	    double[] logalpha2 = new double[no_of_steps]; 
	    double loglike = 0; 
	    double logstat1, logstat2, prefactor1, prefactor2, denom1, denom2;
	    int no_of_tracks = trajs.length;
	    
	    d1=Math.pow(10,d1);
	    d2=Math.pow(10,d2);
	    
	    // Calculate log of transition matrix and stationary probabilities for the given transition probabilities
	    //TODO log(MxMxMxM....)
	    double logtrans[] = {Math.log(1 - p12), Math.log(p21), Math.log(p12), Math.log(1 - p21)};
	    logstat1 = Math.log( p21/(p12 + p21) );
	    logstat2 = Math.log( p12/(p12 + p21) );
	    
	    // Pre-calculations for the log-likelihood computation
	    prefactor1 = -Math.log(d1*deltaT);
	    denom1 = 4*d1*deltaT;
	    prefactor2 = -Math.log(d2*deltaT);
	    denom2 = 4*d2*deltaT;
	    System.out.println("deltaT:"+deltaT);
	    System.out.println("d1:"+d1);
        System.out.println("d2:"+d2);
	    System.out.println("prefactor1:"+prefactor1);
        System.out.println("prefactor2:"+prefactor2);
	    
	    // Calculate log of the forward variable alpha
	    //TODO revisar!
//	    int n = no_of_steps;
        int n = sq_dis.length;
	    for (int j=0; j<no_of_tracks; j++)
	    {
	    	sq_dis = trajs[j].getSqDis(); 
//	    	sq_dis = trajs[j].getSqDispPro();
	    	n = sq_dis.length;
	    	logalpha1 = new double[n];
		    logalpha2 = new double[n]; 
		    
	        logalpha1[0] = prefactor1 - sq_dis[0]/denom1 + logstat1;
	        logalpha2[0] = prefactor2 - sq_dis[0]/denom2 + logstat2;
	        System.out.println("logalpha1:"+logalpha1[0]);
            System.out.println("logalpha2:"+logalpha2[0]);
	        for (int i=1; i<n; i++)
	        {
	            logalpha1[i] = prefactor1 - sq_dis[i]/denom1 + logsum( logalpha1[i-1] + logtrans[0], logalpha2[i-1] + logtrans[1] );
	            logalpha2[i] = prefactor2 - sq_dis[i]/denom2 + logsum( logalpha1[i-1] + logtrans[2], logalpha2[i-1] + logtrans[3] );
	        }
	        // Calculate the log-likelihood for each track and sum over all the tracks.
	        loglike +=  logsum( logalpha1[n-1], logalpha2[n-1] );
	        //System.out.println("loglike"+loglike);
	    }
		return loglike;
	}
	public static double logsum(double a, double b)
	{
		return (a>b) ? a+Math.log(1+Math.exp(b-a)) : b+Math.log(1+Math.exp(a-b));
	}
	


	
	

	
	
	
}

