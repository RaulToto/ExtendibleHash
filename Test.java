

class Bucket {
    private Registro<Integer> e1 = new Registro<>();
    private Registro<Integer> e2 = new Registro<>();
    private int localDepth = 2;
    public Bucket()
        {
        }
    
    public boolean isFull()
        {           
            if(e1.getIsFull() && e2.getIsFull())
                {
                    return true;
                }
            
            else{
                    return false;
                }
        }
    public boolean isEmpty()
        {
            if((!e1.getIsFull()) && (!e2.getIsFull()))
                {
                    return true;
                }else
                    {
                        return false;
                    }
        }

    public void setElemOne(int val, boolean f)
        {
            e1.setValue(val);
            e1.setIsFull(f);
        }
    public Registro getElemOne()
        {
            return e1;
        }
    public void setElemTwo(int val, boolean f)
        {
            e2.setValue(val);
            e2.setIsFull(f);
        }
    public Registro getElemTwo()
        {
            return e2;
        }
    public int getLocalDepth()
        {
            return localDepth;
        }
    public void setLocalDepth(int val)
        {
            localDepth = val;
        }
    /*
    public boolean remove(int key) {
        if(currCap > 0) {
        for(int i = 0; i < 4; ++i) {
            if(this.data[i] != null && this.data[i].key == key) {
            this.data[i] = null;
            this.currCap--;
            return true;
            }
        }
        }
        return false;
    }*/

}

class Registro<T> {
    
    private int value = 0;
    T registro;
    private boolean isFull = false;
    
    public Registro(){}
    
    public void setValue(int val)
        {
            value = val;
        }
    public int getValue()
        {
            return value;
        }
    
    public void setIsFull(boolean val)
        {
            isFull = val;
        }
    public boolean getIsFull()
        {
            return isFull;
        }
} 
class ExtendibleHash {
    
    public static Bucket[] addRegistry(int value, Bucket[] bucks)
    {
            int position = value%((int)Math.pow(2,(int)(Test.globalDepth)));
            System.out.println(position+"value");
            if(!bucks[position].isFull())
            {
                if(!bucks[position].getElemOne().getIsFull())
                {
                    bucks[position].getElemOne().setValue(value);
                    bucks[position].getElemOne().setIsFull(true);
                }
                else 
                {
                    bucks[position].getElemTwo().setValue(value);
                    bucks[position].getElemTwo().setIsFull(true);
                }
            }
            else
            {   
                bucks = collisionDetected(bucks, value, position);
            }
            
            return bucks;
    }
    
    public static Bucket[] collisionDetected(Bucket[] buckets, int value, int probBucket)
        {
            int position = value %((int)Math.pow(2, (int)(Test.globalDepth)));
        
            if(buckets[position].getLocalDepth()<Test.globalDepth)
                {
                    buckets = splitBucket(buckets, value, probBucket);
                }else
                    {
                        buckets = doubleDirectory(buckets);
                        buckets = addRegistry(value, buckets);
                    }
         
            return buckets;
        }
    
    public static Bucket[] doubleDirectory(Bucket[] buckets)
        {
            Test.globalDepth = Test.globalDepth + 1;
            int arrayLen = (int)Math.pow(2, Test.globalDepth);
            Bucket[] newBuckets = new Bucket[arrayLen];
            
            for(int q=0; q<arrayLen; q++)
                {
                    if(q<((int) Math.pow(2, (Test.globalDepth-1))))
                        {
                            newBuckets[q]= buckets[q];
                        }else
                            {
                                newBuckets[q] = new Bucket();
                                newBuckets[q].getElemOne().setIsFull(false);
                                newBuckets[q].getElemTwo().setIsFull(false);
                            }
                }
        
            return newBuckets;
        }
    
    public static Bucket[] splitBucket(Bucket[] buckets, int value, int probBucket)
        {
                        int temp1a = buckets[probBucket].getElemOne().getValue();
                        int temp2a = buckets[probBucket].getElemTwo().getValue();
                        //set both poitions to being empty
                        buckets[probBucket].getElemOne().setIsFull(false);
                        buckets[probBucket].getElemTwo().setIsFull(false);
                        
                        //add elements back into the array
                        buckets = addRegistry(temp1a, buckets);
                        buckets = addRegistry(temp2a, buckets);
                        buckets = addRegistry(value, buckets); 
        
            return buckets;
        }
    
    
    
    public static void print(int globalDepth, Bucket[] bucks)
        {
            int loopLen = (int) Math.pow(2, globalDepth);               
                    
            for(int i=0; i<loopLen; i++)
                {
                    if(bucks[i]!=null)
                        {
                            if(!bucks[i].isEmpty())
                                {
                                    if(bucks[i].getElemOne().getIsFull())
                                        {
                                            System.out.println("In bucket "+i+", element one contains: "+bucks[i].getElemOne().getValue());
                                        }
                                    if(bucks[i].getElemTwo().getIsFull())
                                        {
                                            System.out.println("In bucket "+i+", element two contains: "+bucks[i].getElemTwo().getValue());
                                        }
                                
                                }
                        }
                }
        }
    
}

public class Test {
    public static int globalDepth = 2;
    
    public static void main(String[] args) {
        
        Bucket[] bucks = new Bucket[100];
        for(int q=0; q<100; q++){bucks[q]=new Bucket();}   
                                    //value,block 
        bucks = ExtendibleHash.addRegistry(2369, bucks);
        bucks = ExtendibleHash.addRegistry(3760, bucks);
        bucks = ExtendibleHash.addRegistry(4692, bucks);
        bucks = ExtendibleHash.addRegistry(4871, bucks);
        bucks = ExtendibleHash.addRegistry(5659, bucks);
        bucks = ExtendibleHash.addRegistry(1821, bucks);
        bucks = ExtendibleHash.addRegistry(1074, bucks);
        bucks = ExtendibleHash.addRegistry(7115, bucks);
        bucks = ExtendibleHash.addRegistry(1620, bucks);
        
        ExtendibleHash.print(globalDepth, bucks);   
    }
}