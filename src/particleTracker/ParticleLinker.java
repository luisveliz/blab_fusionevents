package particleTracker;


import ij.IJ;

import java.awt.Color;
import java.util.Vector;

import bTools.BMaths;

import data.Frame;
import data.ImageParticle;
import data.Metrics;
import data.Movie;
import data.Particle;
import data.Trajectory;

public class ParticleLinker 
{	
	//TIRFAnalyzer_ tirfAnalyzer;	
	//Movie movie;
	
	//SPT
	public static int linkRange;
	public static int displacement;
		
	//Trajectory[] trajectories;
	//Vector<Trajectory> all_traj; 
	//Cosas del stack
	//ImageStack stack;	
	//StackStatistics stack_stats;
	//UpdatableStackWindow stack_window;
	//StackWindow stack_window;
	//PreviewCanvas pc;
	//float global_max;
	//float global_min;
	//int frames_number;
	
	/*public ParticleTracker(TIRFAnalyzer_ tirfAnalyzer, StackStatistics stack_stats, UserParticleLinkingParams userLinkingParams)
	{
		System.out.println("Nuevo Particle Tracker:");	
	}*/
	
	//public static void linkParticles(SPTMovie movie, UserParticleLinkingParams userLinkingParams)
	public static void linkParticles(Movie movie)
	{
		Frame[] frames = movie.getFrames();
		int frames_number = movie.getLenght();
		
		int m, i, j, k, nop, nop_next, n;
    	int ok, prev, prev_s, x = 0, y = 0, curr_linkrange;
    	int[] g;
    	double min, z, max_cost;
    	double[] cost;
    	ImageParticle[] p1 , p2;
    	
		/////////////////////////////////////
		p1 = new ImageParticle[0];
		//System.out.println("linking particles 0.1");
		//p2;
		///////////////////////////////////////

    	// set the length of the particles next array according to the linkrange
    	// it is done now since link range can be modified after first run
    	for (int fr = 0; fr<frames.length; fr++) {
		//for (int fr = 0; fr<frames_number; fr++) {
    		for (int pr = 0; pr<frames[fr].getParticles().length; pr++) {
    			frames[fr].getParticle(pr).next = new int[linkRange];
    			//System.out.println("linking particles 0.2");
    		}
    	}
    	curr_linkrange = linkRange;
    	//System.out.println("linking particles 1");
    	/* If the linkrange is too big, set it the right value */
    	if(frames_number < (curr_linkrange + 1))
    		curr_linkrange = frames_number - 1;
    	
    	max_cost = displacement * displacement;

    	//Para cada frame m
    	for(m = 0; m < frames_number - curr_linkrange; m++) 
    	{
    		nop = frames[m].getParticles().length;
    		for(i = 0; i < nop; i++) 
    		{
    			frames[m].getParticle(i).special = false;
    			for(n = 0; n < linkRange; n++)
    			{
    				frames[m].getParticle(i).next[n] = -1;
    				//System.out.println("linking particles 1.1");
    			}
    		}
    		//System.out.println("linking particles 2");
    		p1 = frames[m].getParticles();
    		for(n = 0; n < curr_linkrange; n++) 
    		{
    			max_cost = (double)(n + 1) * displacement * (double)(n + 1) * displacement;
  
    			nop_next = frames[m + (n + 1)].getParticles().length;

    			/* Set up the cost matrix */
    			cost = new double[(nop + 1) * (nop_next + 1)];

    			/* Set up the relation matrix */
    			g = new int[(nop + 1) * (nop_next + 1)];
    			
    			/* Set g to zero */
    			for (i = 0; i< g.length; i++) g[i] = 0;

    			
    			//p1 = frames[m].getParticles();
    			p2 = frames[m + (n + 1)].getParticles();
    		
    			/* Fill in the costs */
    			for(i = 0; i < nop; i++) {
    				for(j = 0; j < nop_next; j++) {
    					cost[BMaths.coord(i, j, nop_next + 1)] = (p1[i].x - p2[j].x)*(p1[i].x - p2[j].x) + 
    					(p1[i].y - p2[j].y)*(p1[i].y - p2[j].y) + 
    					(p1[i].m0 - p2[j].m0)*(p1[i].m0 - p2[j].m0) + 
    					(p1[i].m2 - p2[j].m2)*(p1[i].m2 - p2[j].m2);
    				}
    			}
    			//System.out.println("linking particles 3");
    			for(i = 0; i < nop + 1; i++)
    				cost[BMaths.coord(i, nop_next, nop_next + 1)] = max_cost;
    			for(j = 0; j < nop_next + 1; j++)
    				cost[BMaths.coord(nop, j, nop_next + 1)] = max_cost;
    			cost[BMaths.coord(nop, nop_next, nop_next + 1)] = 0.0;

    			/* Initialize the relation matrix */
    			for(i = 0; i < nop; i++) { // Loop over the x-axis
    				min = max_cost;
    				prev = 0;
    				for(j = 0; j < nop_next; j++) { // Loop over the y-axis
    					/* Let's see if we can use this coordinate */
    					ok = 1;
    					for(k = 0; k < nop + 1; k++) {
    						if(g[BMaths.coord(k, j, nop_next + 1)] == 1) {
    							ok = 0;
    							break;
    						}
    					}
    					if(ok == 0) // No, we can't. Try the next column
    						continue;

    					/* This coordinate is OK */
    					if(cost[BMaths.coord(i, j, nop_next + 1)] < min) {
    						min = cost[BMaths.coord(i, j, nop_next + 1)];
    						g[BMaths.coord(i, prev, nop_next + 1)] = 0;
    						prev = j;
    						g[BMaths.coord(i, prev, nop_next + 1)] = 1;
    					}
    				}
    				//System.out.println("linking particles 4");
    				/* Check if we have a dummy particle */
    				if(min == max_cost) {
    					g[BMaths.coord(i, prev, nop_next + 1)] = 0;
    					g[BMaths.coord(i, nop_next, nop_next + 1)] = 1;
    				}
    			}

    			/* Look for columns that are zero */
    			for(j = 0; j < nop_next; j++) {
    				ok = 1;
    				for(i = 0; i < nop + 1; i++) {
    					if(g[BMaths.coord(i, j, nop_next + 1)] == 1)
    						ok = 0;
    				}

    				if(ok == 1)
    					g[BMaths.coord(nop, j, nop_next + 1)] = 1;
    			}

    			/* The relation matrix is initilized */

    			/* Now the relation matrix needs to be optimized */
    			min = -1.0;
    			while(min < 0.0) {
    				min = 0.0;
    				prev = 0;
    				prev_s = 0;
    				for(i = 0; i < nop + 1; i++) {
    					for(j = 0; j < nop_next + 1; j++) {
    						if(i == nop && j == nop_next)
    							continue;

    						if(g[BMaths.coord(i, j, nop_next + 1)] == 0 && 
    								cost[BMaths.coord(i, j, nop_next + 1)] <= max_cost) {
    							/* Calculate the reduced cost */

    							// Look along the x-axis, including
    							// the dummy particles
    							for(k = 0; k < nop + 1; k++) {
    								if(g[BMaths.coord(k, j, nop_next + 1)] == 1) {
    									x = k;
    									break;
    								}
    							}

    							// Look along the y-axis, including
    							// the dummy particles
    							for(k = 0; k < nop_next + 1; k++) {
    								if(g[BMaths.coord(i, k, nop_next + 1)] == 1) {
    									y = k;
    									break;
    								}
    							}

    							/* z is the reduced cost */
    							if(j == nop_next)
    								x = nop;
    							if(i == nop)
    								y = nop_next;

    							z = cost[BMaths.coord(i, j, nop_next + 1)] + 
    							cost[BMaths.coord(x, y, nop_next + 1)] - 
    							cost[BMaths.coord(i, y, nop_next + 1)] - 
    							cost[BMaths.coord(x, j, nop_next + 1)];
    							if(z > -1.0e-10)
    								z = 0.0;
    							if(z < min) {
    								min = z;
    								prev = BMaths.coord(i, j, nop_next + 1);
    								prev_s = BMaths.coord(x, y, nop_next + 1);
    							}
    						}
    					}
    				}

    				if(min < 0.0) {
    					g[prev] = 1;
    					g[prev_s] = 1;
    					g[BMaths.coord(prev / (nop_next + 1), prev_s % (nop_next + 1), nop_next + 1)] = 0;
    					g[BMaths.coord(prev_s / (nop_next + 1), prev % (nop_next + 1), nop_next + 1)] = 0;
    				}
    			}

    			/* After optimization, the particles needs to be linked */
    			for(i = 0; i < nop; i++) 
    			{
    				for(j = 0; j < nop_next; j++) 
    				{
    					if(g[BMaths.coord(i, j, nop_next + 1)] == 1)
    					{
    						p1[i].next[n] = j;    					
    						frames[m].getParticle(i).next[n]=j;
    						////System.out.println("Linkeada "+i+" con "+j+" con linkrange "+n+" en frame "+m);
    					}    					
    				}
    			}    			
    		}
    		//System.out.println("linking particles 5");
    		////////////////////////////////////////////////
    		//frames[m].setParticles(p1);
    		////////////////////////////////////////////////

    		if(m == (frames_number - curr_linkrange - 1) && curr_linkrange > 1)
    			curr_linkrange--;
    	}

    	/* At the last frame all trajectories end */
    	for(i = 0; i < frames[frames_number - 1].getParticles().length; i++) 
    	{
    		frames[frames_number - 1].getParticle(i).special = false;
    		for(n = 0; n < linkRange; n++)
    			frames[frames_number - 1].getParticle(i).next[n] = -1;
    	}
    	//System.out.println("linking particles 6");
		movie.setFrames(frames);		
	}
	public static Trajectory[] generateTrajectories(Movie movie)
	{
		Frame[] frames = movie.getFrames();
		int frames_number = movie.getLenght();
		//System.out.println("1");
		Vector<Trajectory> all_traj;
		
		int i, j, k;
		int found, n, m;
		// Bank of colors from which the trajectories color will be selected
		Color[] col={Color.blue,Color.green,Color.orange,Color.cyan,Color.magenta,Color.yellow,Color.white,Color.gray,Color.pink};
		
//		TrajectoryImage curr_traj;
		Trajectory curr_traj;
		//int[] curr_traj_frames;
		// temporary vector to hold particles for current trajectory
		Vector<Particle> curr_traj_particles = new Vector<Particle>(frames_number);		
		Vector<Integer> curr_traj_frames = new Vector<Integer>();   
		// initialize trajectories vector
		all_traj = new Vector<Trajectory>();
		int number_of_trajectories = 0;	
		int n_trajs_considered = 0;
		//System.out.println("2");
		for(i = 0; i < frames_number; i++) 
		{
			for(j = 0; j < frames[i].getParticles().length; j++) 
			{
				if(!frames[i].getParticle(j).special)
				{
					frames[i].getParticle(j).special = true;
					found = -1;
					//System.out.println("3");
					// go over all particles that this particle (particles[j]) is linked to
					for(n = 0; n < linkRange; n++) {
						// if it is NOT a dummy particle - stop looking
						if(frames[i].getParticle(j).next[n] != -1) {
							found = n;
							//System.out.println("4");
							break;							
						}
					}
					//System.out.println("5");
					// if this particle is not linked to any other
					// go to next particle and dont add a trajectory
					if(found == -1)
						continue;
					
					// Added by Guy Levy, 18.08.06 - A change form original implementation
					// if this particle is linkd to a "real" paritcle that was already linked
					// break the trajectory and start again from the next particle. dont add a trajectory
					if(frames[i + n + 1].getParticle(frames[i].getParticle(j).next[n]).special) 
						continue;

					// this particle is linked to another "real" particle that is not already linked
					// so we have a trajectory
					number_of_trajectories++;					
					curr_traj_particles.add(frames[i].getParticle(j));
					curr_traj_frames.add((Integer)frames[i].getParticle(j).getMovieFrame());
					k = i;
					m = j;
					do {
						//System.out.println("do");
						found = -1;
						for(n = 0; n < linkRange; n++) {
							//System.out.println("6");
							if(frames[k].getParticle(m).next[n] != -1) {
								// If this particle is linked to a "real" particle that
								// that is NOT already linked, continue with building the trajectory
								if(frames[k + n + 1].getParticle(frames[k].getParticle(m).next[n]).special == false) {
									found = n;
									break;
							    // Added by Guy Levy, 18.08.06 - A change form original implementation
								// If this particle is linked to a "real" particle that
								// that is already linked, stop building the trajectory
								} else {									
									break;
								}
							}
						}
					//	System.out.println("7");
						if(found == -1)
							break;
						m = frames[k].getParticle(m).next[found];
						k += (found + 1);
						curr_traj_particles.add(frames[k].getParticle(m));
						curr_traj_frames.add((Integer)frames[k].getParticle(m).getMovieFrame());
						frames[k].getParticle(m).special = true;
					} while(m != -1);					
					System.out.println("8");
					
					
					// Create the current trajectory
					
					//System.out.println("Creando trayectoria: "+number_of_trajectories);
					if(curr_traj_particles.size()>4)//TODO no olvidar esto!!!!
					{
						n_trajs_considered++;
						IJ.log("Creando trayectoria: "+n_trajs_considered);
						ImageParticle[] curr_traj_particles_array = new ImageParticle[curr_traj_particles.size()];
						int[] intFrames = new int[curr_traj_frames.size()];
						int firstFrame = curr_traj_frames.get(0);
						System.out.println("firstFrame: "+firstFrame);
						//System.out.println("firstFrame: "+curr_traj_frames.get(0));
						
						for(int w=0;w<intFrames.length;w++)
						{
							intFrames[w]=curr_traj_frames.get(w)-firstFrame+1;
						}
						
//						curr_traj = new TrajectoryImage(n_trajs_considered, (Particle[])curr_traj_particles.toArray(curr_traj_particles_array), intFrames, curr_traj_frames.get(0));
						curr_traj = new Trajectory(n_trajs_considered, (Particle[])curr_traj_particles.toArray(curr_traj_particles_array), intFrames, new Metrics());
						System.out.println("traj creada");
						curr_traj.setMovieFrame(curr_traj_frames.get(0));
						
						
						//System.out.println(curr_traj.toString());
						
						// set current trajectory parameters
						curr_traj.setColor(col[n_trajs_considered % col.length]);
						
						//add current trajectory to all_traj vactor
						System.out.println("agregando traj");
						all_traj.add(curr_traj);
						System.out.println("traj agregada");
					}
					// clear temporary vector
					curr_traj_particles.removeAllElements();
					curr_traj_frames.removeAllElements();
					System.out.println("12");
				}				
			}
		}		
		
		Trajectory[] trajectories = new Trajectory[all_traj.size()];
		System.out.println("trajectories linked:"+trajectories.length);
		for(int ii=0; ii< trajectories.length;ii++)
		{
			trajectories[ii]=all_traj.get(ii);			
		}
		//System.out.println("12");
//		movie.setTrajectories(trajectories);
		return trajectories;
		//System.out.println("13");
	}

	/*public Movie getMovie() {
		return movie;
	}*/

}
