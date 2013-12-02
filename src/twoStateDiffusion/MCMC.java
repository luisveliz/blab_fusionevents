package twoStateDiffusion;

import java.util.Random;

import bTools.BNF;

import data.Trajectory;

public class MCMC extends Thread
{
	final Trajectory[] trajs;
	final GUI gui_hmm;
	final double delta_t;
	final double[] initial;
	final int steps;
	public boolean pause;
	public boolean finish;
	
	double[] scale;
	int updatePlotsFrequency;
	public Thread mcmc;
	double[][] mcmctraj;
	double[] results;	
	
	public MCMC(final Trajectory[] trajs, final GUI gui_hmm, final double delta_t, final double[] initial, final int steps, double[] scale, int upf)
	{
		this.trajs = trajs;
		this.gui_hmm = gui_hmm;
		this.delta_t = delta_t;
		this.initial = initial;
		this.steps = steps;
		this.scale = scale;
		this.updatePlotsFrequency = upf;
		finish = false;
		pause = false;
	}

	public void run() {
			
		
		//FileWriter ryt;
		//try {
			/*ryt = new FileWriter("E:\\Escritorio\\MCMCinfo.txt");
		
		    BufferedWriter out=new BufferedWriter(ryt);
    		out.write("MCMC info\n");
    		
    		out.write("N° trajs: "+trajs.length+"\n");
    		out.write("delta_t: "+delta_t+"\n");
    		out.write("intial guess: d1:"+initial[0]+" d2:"+initial[1]+" p12:"+initial[2]+" p21:"+initial[3]+"\n");
    		out.write("MCMC steps: "+steps+"\n");
    		out.write("\n");*/
		
			// MCMC parameter optimization
		    // Create and initialize matrices for MCMC optimization
			int mcmcsteps = 4*steps; 
		    if(mcmcsteps%4>0)
		    {
		    	mcmcsteps += 4-mcmcsteps%4;
		    }
			double[] mcmcvalue = new double[mcmcsteps];
			
		    //double[][] mcmctraj = new double[mcmcsteps][4];
			mcmctraj = new double[mcmcsteps][4];
		    mcmctraj[0]=initial;
		    double[] proposed = new double[4];
		    double[] accepted = new double[4];
		    double[] acceptance_ratio = new double[4];
		    int tries = 0;
		    int stepsCount = 0;
		    
		    // Calculate log-likelihood for previous parameter set
		    double[] parold = initial;
		    double logpostold = HMM.logLike(trajs, delta_t, parold[0],parold[1],parold[2],parold[3]);
		    mcmcvalue[0]=logpostold;
		    System.out.println("loglikepostold:"+logpostold);
		    
		    
		    double[] partest = new double[4];
		    double logposttest=0;
		    
		    boolean constraint;
		    boolean bounded;
		    
		    Random random = new Random();
		    mcmcsteps--;
		    while (tries < mcmcsteps && !finish)
		    {
		    	while(pause)
	    		{
		    		try {
						sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
		    	//System.out.println("tries:"+tries);
		        partest[0] = parold[0];
		        partest[1] = parold[1];
		        partest[2] = parold[2];
		        partest[3] = parold[3];
		        // Propose a normally distributed displacement, sequentially along each parameter axis
		        for(int k=0; k<4; k++)
		        {
		        	//out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"\n");
		        	double[] aux=new double[4];
		        	aux[0]=parold[0];
		        	aux[1]=parold[1];
		        	aux[2]=parold[2];
		        	aux[3]=parold[3];
		            proposed[k]++;
		            if(k==0 || k==1)
		            {
		            	// Impose the constraint D2 < D1
	                    constraint = false;
	                    while(!constraint)
	                    {
	                        //partest[k] = normrnd( parold[k], scale[k] );
	                    	//partest[k] = parold[k] + random.nextGaussian()*scale[k];
	                    	//out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"\n");
	                    	partest[k] = aux[k] + random.nextGaussian()*scale[k];
	                    	//out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"\n");
	                    	//System.out.println("partest "+partest[k]);
	                        if (partest[1] < partest[0])
	                            constraint = true;
	                    }
		            }
		            else
		            {
		            	// Check for the transition probabilitites to be bounded
		                bounded = false;
		                while(!bounded)
	                    {
	                        //partest[k] = normrnd( parold[k], scale[k] );
		                	//partest[k] = parold[k] + random.nextGaussian()*scale[k];
		                	//out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"\n");
		                	partest[k] = aux[k] + random.nextGaussian()*scale[k];
		                	//out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"\n");
		                	//System.out.println("bounded: "+bounded);
	                        if(partest[k] > 0 && partest[k] < 1)
	                            bounded = true;
	                    }
		            }
		            // Calculate log-likelihood after proposed displacement
		            //System.out.println("loglike...");
		            //System.out.println("partest: "+partest[0]+" "+partest[1]+" "+partest[2]+" "+partest[3]);
		            //out.write("trie:"+tries+"\n");
		            logposttest = HMM.logLike(trajs, delta_t, partest[0],partest[1],partest[2],partest[3]);
		            //System.out.println("loglikeposttest:"+logposttest);
		            
		            // Metropolis rejection
		            double logrand = Math.log(random.nextDouble());
		            //spt_gui.jTextArea_Debug_addText("tries:"+tries+" d1:"+Math.pow(10,partest[0])+" d2:"+Math.pow(10,partest[1])+" p12:"+partest[2]+" p21:"+partest[3]+"\n");
		            //out.write(" d1:"+Math.pow(10,partest[0])+" d2:"+Math.pow(10,partest[1])+" p12:"+partest[2]+" p21:"+partest[3]+"\n");
		            //out.write("old:"+logpostold+" prop:"+logposttest+" logrand:"+logrand);
		            //out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"			"+logrand);
		            if(((logposttest - logpostold) > 0) || (logrand < (logposttest - logpostold)))//Accept the move
		            {
		            	//out.write("	1\n");
		            	for(int i=0;i<4;i++)
		            	{
		            		mcmctraj[tries][i] = partest[i];
		            		parold[i] = partest[i];
		            	}
		            	mcmcvalue[tries] = logposttest;
		            	accepted[k]++;
		            	logpostold = logposttest;
		                //out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"			"+logrand+"\n");
		                //System.out.println("logposttest:"+logposttest);
		                //System.out.println("Atries:"+tries+" d1:"+Math.pow(10,partest[0])+" d2:"+Math.pow(10,partest[1])+" p12:"+partest[2]+" p21:"+partest[3]+" logposttest:"+logposttest);
		            }    
		            else //Reject the move
		            { 
		            	//out.write("	0\n");
		            	for(int i=0;i<4;i++)
		            	{
		            		mcmctraj[tries][i] = parold[i];
		            	}
		            	mcmcvalue[tries] = logpostold;
		                //out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"			"+logrand+"\n");
		                //System.out.println("Rtries:"+tries+" d1:"+Math.exp(partest[0])+" d2:"+Math.exp(partest[1])+" p12:"+partest[2]+" p21:"+partest[3]+" logposttest:"+logposttest);
	                }
		            //out.write(tries+"	"+Math.pow(10,parold[0])+"			"+Math.pow(10,parold[1])+"			"+parold[2]+"			"+parold[3]+"			"+logpostold+"			"+logposttest+"			"+logrand+"\n");
		            //out.write(tries+"	"+Math.pow(10,mcmctraj[tries][0])+"			"+Math.pow(10,mcmctraj[tries][1])+"			"+mcmctraj[tries][2]+"			"+mcmctraj[tries][3]+"\n\n");
		            tries = tries + 1;
		            
		        }
		        stepsCount++;
//		        if(tries%updatePlotsFrequency==0)
		        if(stepsCount%updatePlotsFrequency==0)
		        {
		        	acceptance_ratio[0] = accepted[0]/proposed[0];
				    acceptance_ratio[1] = accepted[1]/proposed[1];
				    acceptance_ratio[2] = accepted[2]/proposed[2];
				    acceptance_ratio[3] = accepted[3]/proposed[3];
				    for(int p=0;p<4;p++)
				    {
				    	accepted[p]=0;
				    	proposed[p]=0;
				    }
				    gui_hmm.jTextfield_HMM_MCMC_AccRatioD1_setValue(BNF.sF(acceptance_ratio[0]));
				    gui_hmm.jTextfield_HMM_MCMC_AccRatioD2_setValue(BNF.sF(acceptance_ratio[1]));
				    gui_hmm.jTextfield_HMM_MCMC_AccRatioP12_setValue(BNF.sF(acceptance_ratio[2]));
				    gui_hmm.jTextfield_HMM_MCMC_AccRatioP21_setValue(BNF.sF(acceptance_ratio[3]));
//			        gui_hmm.getJFreeChart_HMM_MCMC_d1d2().addData(Math.pow(10,parold[0]),Math.pow(10,parold[1]));
//		            gui_hmm.getJFreeChart_HMM_MCMC_p12p21().addData(parold[2],parold[3]);
		            gui_hmm.getJFreeChart_MCMCsteps().addLogLikeValue(logpostold);
		            gui_hmm.getJFreeChart_MCMCsteps().addDifValue(Math.pow(10,parold[0]),Math.pow(10,parold[1]));
		            gui_hmm.getJFreeChart_MCMCsteps().addProbValue(parold[2],parold[3]);
		            gui_hmm.jProgressBar_HMM_MCMC_setValue(100*tries/mcmcsteps);
		            //spt_gui.jTextArea_Debug_addText("tries:"+tries+" d1:"+Math.pow(10,partest[0])+" d2:"+Math.pow(10,partest[1])+" p12:"+partest[2]+" p21:"+partest[3]);
		        }
		    }
		    acceptance_ratio[0] = accepted[0]/proposed[0];
		    acceptance_ratio[1] = accepted[1]/proposed[1];
		    acceptance_ratio[2] = accepted[2]/proposed[2];
		    acceptance_ratio[3] = accepted[3]/proposed[3];
		    /*out.write("tries:"+tries+"\n");
		    out.write("Estimated d1:"+Math.exp(partest[0])+"\n");
		    out.write("Estimated d2:"+Math.exp(partest[1])+"\n");
		    out.write("Estimated p12:"+partest[2]+"\n");
		    out.write("Estimated p21:"+partest[3]+"\n");
		    out.write("Acceptance ratio0:"+acceptance_ratio[0]+"\n");
		    out.write("Acceptance ratio1:"+acceptance_ratio[1]+"\n");
		    out.write("Acceptance ratio2:"+acceptance_ratio[2]+"\n");
		    out.write("Acceptance ratio3:"+acceptance_ratio[3]+"\n\n");*/
		    gui_hmm.hmm_MCMC_setEnabled(true);
		    gui_hmm.jProgressBar_HMM_MCMC_setValue(0);
		    
		    //out.write("End");
		    //out.close();
		    
		    finish=true;
		    gui_hmm.jButton_MCMC_setText("start");
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}


	public void calculateResults(int burn)
	{
		results = new double[4];
			
		for(int i=burn-1;i<mcmctraj.length;i++)
		{
			results[0]+=Math.pow(10,mcmctraj[i][0]);
			results[1]+=Math.pow(10,mcmctraj[i][1]);
			results[2]+=mcmctraj[i][2];
			results[3]+=mcmctraj[i][3];
		}
		results[0]/=mcmctraj.length-burn-1;
		results[1]/=mcmctraj.length-burn-1;
		results[2]/=mcmctraj.length-burn-1;
		results[3]/=mcmctraj.length-burn-1;	
	}
	
	public double[] getResults()
	{
		return results;
	}
	public void setScale(double[] scale)
	{
		this.scale = scale;
	}
	public void setUpdateFrecuency(int upf)
	{
		this.updatePlotsFrequency = upf;
	}

}
