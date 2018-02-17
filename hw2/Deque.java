import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private class ItemNode{
        Item item;
        ItemNode next;
        ItemNode prev;

        public ItemNode(Item i, ItemNode n){
            item = i;
            next = n;
        }

        public ItemNode(){}
    }

    private ItemNode sentinel;
    private ItemNode tail;
    private int size;

    private class DequeIterator implements Iterator<Item>{
      private ItemNode curr = sentinel.next;

      public boolean hasNext(){
          return curr != null;
      }
      
      public void remove(){
          throw new java.lang.UnsupportedOperationException();
      }

      public Item next(){
          if(!hasNext()){
              throw new java.util.NoSuchElementException();
          }
          Item tempItem = curr.item;
          curr = curr.next;
          return tempItem;
      }

    }
    
    
   /** construct an empty deque */
    public Deque(){
        sentinel = new ItemNode();
        tail = sentinel;
        size = 0;
    }

   /** is the deque empty? */
   public boolean isEmpty(){
       return size == 0;
   }


   /** return the number of items on the deque */
   public int size(){
       return size;
   } 
   /** add the item to the front */                       
   public void addFirst(Item item){
       if(item ==  null){
           throw new java.lang.IllegalArgumentException();
       }
       // copy of olf first
       ItemNode oldfirst = sentinel.next;
       // topology: sentinel -> <-first -> <-oldfirst
       // accomplish four connections
       sentinel.next = new ItemNode(item, sentinel.next);
       // build releationship between old first and new first
       if(oldfirst != null){
           oldfirst.prev = sentinel.next;
           sentinel.next.next = oldfirst;
       }
       // complete relationship between sentinel and new first
       sentinel.next.prev = sentinel;
       // increase size
       size++;
       if(size == 1){
           tail = sentinel.next;
       }
   }

   /** add the item to the end */         
   public void addLast(Item item){
       if(item ==  null){
           throw new java.lang.IllegalArgumentException();
       }
       // copy of old tail
       ItemNode oldtail = tail;

       // oldtail -> <- new tail
       // accomplish two connections
       tail.next = new ItemNode(item, null);
       tail = tail.next;
       tail.prev = oldtail;
       // increase size
       size++;
   }
   /** remove and return the item from the front */           
   public Item removeFirst(){
       if(size == 0){
           throw new java.util.NoSuchElementException();
       }
       // topology: sentinel -> <-first -> <-newfirst
       ItemNode oldfirst = sentinel.next;
       Item result = oldfirst.item;
       if(size() == 1){
           sentinel.next = null;
           tail = sentinel;
           size = 0;
           return result;
       }
       // just discard old first and accomplish two connections 
       sentinel.next = sentinel.next.next;
       sentinel.next.prev = sentinel;
       // decrease size
       size--;
       return result;

   }

   /** remove and return the item from the end */                
   public Item removeLast(){
       if(size == 0){
           throw new java.util.NoSuchElementException();
       }
       ItemNode newlast = tail.prev;
       Item result = tail.item;
       // simply discard the old last node
       newlast.next = null;
       tail = newlast;
       size--;
       return result;
   }

   /** return an iterator over items in order from front to end */                 
   public Iterator<Item> iterator(){
      return new DequeIterator();
   }

   /** unit testing (optional) */

   public static void main(String[] args){
       // System.out.println("Hello World!");
       Deque <Integer> d = new Deque<>();
       d.addLast(2);
       d.addFirst(1);
       System.out.println(d.removeFirst());
       System.out.println(d.removeLast());
       // Deque.DequeIterator di = d.new DequeIterator();
       // while(di.hasNext()){
       //     System.out.println(di.next());
       // }
   }   
}