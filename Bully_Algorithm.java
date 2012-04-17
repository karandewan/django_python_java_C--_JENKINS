/**************************************************************************************************************************************************
 * Name: Karan Dewan
 * Algorithm Implemented: BullyAlgorithm
 * Version: 1.0
 **************************************************************************************************************************************************/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;
public class Bully_Algorithm implements Runnable                                 //Main Class which implement Runnable class 
{
	public static  Map master=new HashMap();    								// This map variable store which process is master i.e. co-ordinator
	public static Map record= new HashMap();								   	// This map variable store which processes are alive 
	public static Thread Process3;												// This is the thread that represent process 3
	public static Thread Process2;												// This is the thread that represent process 2
	public static Thread Process1;												// This is the thread that represent process 1
	public static Thread Process0;												// This is the thread that represent process 0
	
	public synchronized void run()												// This function is always called when the thread is created and everything 
																				//	related to thread is implemented inside this
	{
		
		
			if(Thread.currentThread().getName().equals("3"))                   // This block run only if thread name 3 is running
			{
																		
				String value="Master";
				master.put(Thread.currentThread().getName(),value );		   // Since this is the highest id process it declare itself as master as 
																			  //  soon it start running. 
				record.put(Thread.currentThread().getName(),"Working" );	   // Thread 3 publish alive for other processes to know this it is alive 		
				System.out.println("Process: "+Thread.currentThread().getName()+" declare itself Master");
				//Process3.suspend();                                            // Thread 3 is suspended and is resume by process 1 so that how request is 
																			   // send to master can be displayed. 
				record.remove(Thread.currentThread().getName());               // Thread 3 publish itself that is it retiring.
				System.out.println("Process 3 is going");
			}
			
			if(Thread.currentThread().getName().equals("1"))                   //This block run only if thread name 1 is running
			{
				
				System.out.println("Process: "+Thread.currentThread().getName()+" is up working");  
				record.put(Thread.currentThread().getName()," Working" );     //Thread 1 (Process 1) publish alive for other processes to know this that it is alive
				
				String Process=	master.keySet().toString();
				Process3.resume();                                           // It resume the thread 3 (Process 3) and this will help to display the request process. 
				if(Process3.isAlive())										 // This block run if thread 3 (Process 3) is alive i.e. if master is alive.	
				{
					System.out.println("Process 1 sending the request to the master for process 2");  
					Process=Process.substring(1);
					Process=Process.substring(0, (Process.length()-1));
					if(record.containsKey("2"))                             // This block checks whether the particular process is alive or not 
																			// and if alive master process will 
																		   //approve the request otherwise it will reject the request
					{
						System.out.println("Master Process: "+ Process+ " approve the request");
					}
					else
						System.out.println("Master Process: "+ Process+ " disapprove the request");
				}
				else if(master.containsKey("3"))							// This block run when master process 3 is not working and 
																			// no other process have declare itself as master.
				{
					System.out.println("Process 1 waiting for Master to reply");
					try
					{
						Thread.currentThread().wait(10);	              
					}
					catch(Exception e)
					{
						System.out.println("Process 1 waited for long, Process 1 is starting the election");
						System.out.println("This master is not working so I (Process 1) am electing myself master");
						master.remove("3");                                  // Thread 1( Process 1) declare itself as master.    
						master.put(Thread.currentThread().getName(),"Master");
					}
				}
				else                                                         // This block runs if some process other than process 3 declare itself as master. 
				{
					String master_process=master.keySet().toString();
					String current_Thread=Thread.currentThread().getName();
					master_process=master_process.substring(1, (master_process.length()-1));
					int current_process_check=Integer.parseInt(current_Thread);
					int master_process_check= Integer.parseInt(master_process);
					if(master_process_check>current_process_check)          //If that process id is higher than thread 1( process 1) 
																			//than process 1 send the request to it.
					{
						System.out.println("I cannot be master");
						
					}
					else                                                   // If that process id is lower than thread 1 (process 1) than process bully it 
																		  //  and become master.
					{
						System.out.println(" Since, process 1 id is bigger than master process id I (Process 1) can bully and become master");
						master.remove(master_process);						
						master.put(Thread.currentThread().getName(),"Master");
					}
							
				}				
				Process=master.keySet().toString();
				//t.resume();
				if(Process3.isAlive())                                           //  This code block is same as above but when process 1 sending request for 
																				 //  process zero and this also help in demonstrating other feature of starting 
																				 //  the election and becoming master. 
				{
					System.out.println("Process 1 sending the request to the master for process 0");
				    Process=Process.substring(1);
					Process=Process.substring(0, (Process.length()-1));
					if(record.containsKey("0"))                                  // This block checks whether the particular process is alive or not 
																				 //	and if alive master process will approve the request otherwise it 
																				//  will reject the request
					{
						System.out.println("Master Process: "+ Process+ " approve the request");
					}
					else
						System.out.println("Master Process: "+ Process+ " disapprove the request");
				}
				else if(master.containsKey("3"))                                 // This block run when master process 3 is not working and 
																				//	no other process have declare itself as master.
				{
					System.out.println("Process 1 waiting for Master to reply");
					try
					{
						Thread.currentThread().wait(10);	
					}
					catch(Exception e)
					{
						System.out.println("Process 1 waited for long, Process 1 is starting the election");
						System.out.println("This master is not working so I am electing myself master");
						master.remove("3");
						master.put(Thread.currentThread().getName(),"Master");
					}
				}
				else
				{
					String master_process=master.keySet().toString();
					String current_Thread=Thread.currentThread().getName();
					master_process=master_process.substring(1, (master_process.length()-1));
					int current_process_check=Integer.parseInt(current_Thread);
					int master_process_check= Integer.parseInt(master_process);
					if(master_process_check>current_process_check)
					{
						System.out.println("Process1 cannot be master");
						
					}
					else
					{
						System.out.println("Since, Process 1 have bigger ID I (Process 1) can bully everyone");
						master.remove(master_process);
						master.put(Thread.currentThread().getName(),"Master");
					}
							
				}
			
				record.remove(Thread.currentThread().getName());
				System.out.println("Process 1 is going");
			}


			if(Thread.currentThread().getName().equals("2"))                                // This block run only if thread name 2 is running
			{
				System.out.println("Process: "+Thread.currentThread().getName()+" is up working");
				record.put(Thread.currentThread().getName(),"Working" );                    //Thread 2 (Process 2) publish alive for other processes to know 
																							//this that it is alive
				String Process=	master.keySet().toString();
				Process3.resume();
				if(Process3.isAlive())                                                     // This block run if thread 3 (Process 3) is alive i.e. if master is alive.
				{
					System.out.println("Process 2 sending the request to the master for process 0");

					Process=Process.substring(1);
					Process=Process.substring(0, (Process.length()-1));
					if(record.containsKey("0"))
					{
						System.out.println("Master Process: "+ Process+ " approve the request");
					}
					else
						System.out.println("Master Process: "+ Process+ " disapprove the request");
				}
				else if(master.containsKey("3"))                                         // This block run when master process 3 is not working 
																						 //  and no other process have declare itself as master.
				{
					System.out.println("Process 2 waiting for Master to reply");
					try
					{
						Thread.currentThread().wait(10);	
					}
					catch(Exception e)
					{
						System.out.println("Process 2 waited for long, Process 2 is starting the election");
						System.out.println("This master is not working so I (Process 2) am electing myself master");
						master.remove("3");
						master.put(Thread.currentThread().getName(),"Master");
					}
				}
				else																	// This block runs if some process other than 
																						// process 3 declare itself as master.
				{
					String master_process=master.keySet().toString();                     
					String current_Thread=Thread.currentThread().getName();
					master_process=master_process.substring(1, (master_process.length()-1));
					int current_process_check=Integer.parseInt(current_Thread);
					int master_process_check= Integer.parseInt(master_process);
					if(master_process_check>current_process_check)                     //This block runs when master process id is bigger than this process id.
					{
						System.out.println("I cannot be master");
						
					}
					else                                                              // If that process id is lower than thread 2 (process 2) 
																					  //	than process 2 bully it and become master.
					{
						System.out.println("Since, Process 2 have bigger ID I (Process 2) can bully everyone");
						master.remove(master_process);
						master.put(Thread.currentThread().getName(),"Master");
					}
							
				}
				Process=master.keySet().toString();
				Process3.resume();
				if(Process3.isAlive())                                              // This block runs for the other process request by process 2 to master
				{
					System.out.println("Process 2 sending the request to the master for process 1");
				    Process=Process.substring(1);
					Process=Process.substring(0, (Process.length()-1));
					if(record.containsKey("1"))
					{

						System.out.println("Master Process: "+ Process+ " approve the request");
					}
					else
						System.out.println("Master Process: "+ Process+ " disapprove the request");
					
				}
				else if(master.containsKey("3"))
				{
					System.out.println("Process 2 waiting for Master to reply");
					try
					{
						Thread.currentThread().wait(10);	
					}
					catch(Exception e)
					{
						System.out.println("Process 2 waited for long, Process 2 is starting the election");
						System.out.println("This master is not working so I (Process 2) am electing myself master");
						master.remove("3");
						master.put(Thread.currentThread().getName(),"Master");
					}
				}
				else
				{
					String master_process=master.keySet().toString();
					String current_Thread=Thread.currentThread().getName();
					master_process=master_process.substring(1, (master_process.length()-1));
					int current_process_check=Integer.parseInt(current_Thread);
					int master_process_check= Integer.parseInt(master_process);
					if(master_process_check>current_process_check)
					{
						System.out.println("I cannot be master");
						
					}
					else
					{
						master.remove(master_process);
						master.put(Thread.currentThread().getName(),"Master");
					}
							
				}
				Thread.currentThread().suspend();
				record.remove(Thread.currentThread().getName());
				System.out.println("Process 2 is going");
			}
			if(Thread.currentThread().getName().equals("0"))                        // This block run when thread zero (process zero is created) 
			{
				System.out.println("Process: "+Thread.currentThread().getName()+" is up working");	
				record.put(Thread.currentThread().getName(),"Working" );
				String Process= master.keySet().toString();
				Process3.resume();
				if(Process3.isAlive())
				{
					System.out.println("Process 0 sending the request to the master for process 1");

					Process=Process.substring(1);
					Process=Process.substring(0, (Process.length()-1));
					if(record.containsKey("1"))
					{
						System.out.println("Master Process: "+ Process+ " approve the request");
					}
					else
					{
						System.out.println("Master Process: "+ Process+ " disapprove the request");
					}
				}
				else if(master.containsKey("3"))
				{
					System.out.println("Process 0 waiting for Master to reply");
					try
					{
						Thread.currentThread().wait(10);	
					}
					catch(Exception e)
					{
						System.out.println("Process 0 waited for long, Process 0 is starting the election");
						System.out.println("This master is not working so I (Process 0) am electing myself master");
						master.remove("3");
						master.put(Thread.currentThread().getName(),"Master");
					}
				}
				else
				{
					String master_process=master.keySet().toString();
					String current_Thread=Thread.currentThread().getName();
					master_process=master_process.substring(1, (master_process.length()-1));
					int current_process_check=Integer.parseInt(current_Thread);
					int master_process_check= Integer.parseInt(master_process);
					if(master_process_check>current_process_check)
					{
						master_process=master.keySet().toString();
						System.out.println("Process 0 sending the request to the master for process 1");
						master_process=master_process.substring(1,(master_process.length()-1));
						if(record.containsKey("1"))
						{
							System.out.println("Master Process: "+ master_process+ " approve the request");
						}
						else
							System.out.println("Master Process: "+ master_process+ " disapprove the request");
					}
					else
					{
						System.out.println("Since, Process 0 have bigger ID Process 0 can bully everyone");
						master.remove(master_process);
						master.put(Thread.currentThread().getName(),"Master");
					}
							
				}
				Process= master.keySet().toString();
				Process3.resume();
				if(Process3.isAlive())
				{
					System.out.println("Process 0 sending the request to the master for process 2");

					Process=Process.substring(1);
					Process=Process.substring(0, (Process.length()-1));
					if(record.containsKey("2"))
					{
						System.out.println("Master Process: "+ Process+ " approve the request");
					}
					else
						System.out.println("Master Process: "+ Process+ " disapprove the request");
				}
				else if(master.containsKey("3"))
				{
					System.out.println("Process 0 waiting for Master to reply");
					try
					{
						Thread.currentThread().wait(10);	
					}
					catch(Exception e)
					{
						System.out.println("Process 0 waited for long, Process 0 is starting the election");
						System.out.println("This master is not working so I(Process 0) am electing myself master");
						master.remove("3");
						master.put(Thread.currentThread().getName(),"Master");
					}
				}
				else
				{
					String master_process=master.keySet().toString();
					String current_Thread=Thread.currentThread().getName();
					master_process=master_process.substring(1, (master_process.length()-1));
					int current_process_check=Integer.parseInt(current_Thread);
					int master_process_check= Integer.parseInt(master_process);
					if(master_process_check>current_process_check)
					{
						System.out.println("Process 0 sending the request to the master for process 2");
						if(record.containsKey("2"))
						{
							System.out.println("Master Process: "+ master_process+ " approve the request");
						}
						else
							System.out.println("Master Process: "+ master_process+ " disapprove the request");
						Process2.resume();
					}
					else
					{
						System.out.println("Since, Process 0 have bigger ID Process 0 can bully everyone");
						master.remove(master_process);
						master.put(Thread.currentThread().getName(),"Master");
					}
							
				}
				record.remove(Thread.currentThread().getName());
				System.out.println("Process 0 is going");
			}
		}
	public static void main(String[] args) throws InterruptedException
	{
	Process3=new Thread(new Bully_Algorithm(),"3");                               // New thread object is created and it represent process 3
	Process3.start();
	Thread.currentThread().sleep(1000);
	Process1=new Thread(new Bully_Algorithm(),"1");                              // New thread object is created and it represent process 1
	Process1.start();
	Thread.currentThread().sleep(1000);
	Process2=new Thread(new Bully_Algorithm(),"2");                             // New thread object is created and it represent process 2
	Process2.start();
	Thread.currentThread().sleep(1000);
	Process0=new Thread(new Bully_Algorithm(),"0");                            // New thread object is created and it represent process 0
	Process0.start();
	
	Process3=new Thread(new Bully_Algorithm(),"3");                               // New thread object is created and it represent process 3
	Process3.start();
	}
}