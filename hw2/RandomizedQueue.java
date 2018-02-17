import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
 private Item[] a; // array of items
 private int size;
 private final int initSize = 10; // init array size
    
    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= size;

        // textbook implementation
        // @SuppressWarnings("unchecked")
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;

       // alternative implementation
       // a = java.util.Arrays.copyOf(a, capacity);
    }

    /** nested Deque Iterator class */
    private class RQIterator implements Iterator<Item>{
      private int curr;
      private Item[] remain;
      private int remainSize;


      // initialize
      public RQIterator(){
          remainSize = size;
          curr = (int)(remainSize * StdRandom.uniform()); //random v->[0,n-1]
          // @SuppressWarnings("unchecked")
          remain = (Item[]) new Object[size];
          for (int i = 0; i < size; i++){
              remain[i] = a[i];
          }
          
      }

      public boolean hasNext(){
          return remainSize != 0;
      }
      
      public void remove(){
          throw new java.lang.UnsupportedOperationException();
      }

      public Item next(){
          if(!hasNext()){
              throw new java.util.NoSuchElementException();
          }
          Item tempItem = remain[curr]; // save the pop value
          remain[curr] = remain[remainSize - 1]; // push the last value to the vacant
          remain[remainSize - 1] = null;
          remainSize--;
          if(remainSize == 0){
              curr = 0;
          }
          else curr = (int)(remainSize * StdRandom.uniform());
          return tempItem;
      }

    }
    

   /** construct an empty randomized queue */
   public RandomizedQueue(){
        // @SuppressWarnings("unchecked")
        a = (Item[]) new Object[initSize];
        size = 0;
   }

   /** is the randomized queue empty? */                 
   public boolean isEmpty(){
       return size == 0;
   }

   /** return the number of items on the randomized queue */                
   public int size(){
       return size;
   }

   /** add the item */                        
   public void enqueue(Item item){
       if(item ==  null){
           throw new java.lang.IllegalArgumentException();
       }
       if(size == a.length) resize(2*a.length);
       a[size++] = item;
   }

   /** remove and return a random item */           
   public Item dequeue(){
       if(size == 0){
           throw new java.util.NoSuchElementException();
       }
       int randomNum = (int)(size * StdRandom.uniform());
       Item result = a[randomNum];
       a[randomNum] = a[size - 1];
       a[size - 1] = null;
       size--;
       if (size > 0 && size == a.length/4) resize(a.length/2);
       return result;
   }

   /** return a random item (but do not remove it) */                   
   public Item sample(){
       if(size == 0){
           throw new java.util.NoSuchElementException();
       }
       int randomNum = (int)(size * StdRandom.uniform());
       Item result = a[randomNum];
       return result;
   }

   /** return an independent iterator over items in random order */                    
   public Iterator<Item> iterator(){
       return new RQIterator();
   }

   /** unit testing (optional) */        
   public static void main(String[] args){
       RandomizedQueue <Integer> d = new RandomizedQueue<>();
       d.enqueue(1);
       d.enqueue(2);
       d.enqueue(3);
       d.enqueue(4);
       d.enqueue(6);
       // RandomizedQueue.RQIterator di = d.new RQIterator();
       
//       System.out.println(d.dequeue());
//       System.out.println(d.dequeue());
//       System.out.println(d.dequeue());
//       System.out.println(d.dequeue());
//       while(di.hasNext()){
//           System.out.println(di.next());
//       }

   }   
}