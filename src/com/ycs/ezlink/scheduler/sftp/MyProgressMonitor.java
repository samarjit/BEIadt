package com.ycs.ezlink.scheduler.sftp;

import org.apache.log4j.Logger;

import com.jcraft.jsch.SftpProgressMonitor;

public  class MyProgressMonitor implements SftpProgressMonitor{
	private static Logger logger = Logger.getLogger(MyProgressMonitor.class);  
    long count=0;
    long max=0;
    public void init(int op, String src, String dest, long max){
      this.max=max;
       System.out.println("bytes needs to be transferred is "+ max);
      count=0;
      percent=-1;
   
    }
    private long percent=-1;
    public boolean count(long count){
      this.count+=count;
      if(max==0 ){System.out.println("0 bytes needs to be transferred"); return false;}
      if(percent>=this.count*100/max){ return true; }
      percent=this.count*100/max;

      logger.debug("Completed "+this.count+"("+percent+"%) out of "+max+".");     
      return true;
    }
    public void end(){
      
    }
}