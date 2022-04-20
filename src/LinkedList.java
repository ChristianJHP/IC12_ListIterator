import java.awt.*;

public class LinkedList<E> {

  private Node<E> mHead;
  private int mSize;

  public int size()
  {
    return mSize;
  }

  public void add(int index, E element)
  {
    if (index < 0 || index > size())
      throw new IndexOutOfBoundsException("Index must be between 0 and " + size());
    if (index == 0)
      mHead = new Node<>(element, mHead);
    else {
      Node<E> current = mHead;
      for (int i = 0; i < index - 1; i++)
        current = current.mNext;

      current.mNext = new Node<>(element, current.mNext);
    }
    mSize++;
  }

  public void add(E element)
  {
    add(mSize, element);
  }

  public void addLast(E element)
  {
    add(element);
  }

  public void addFirst(E element)
  {
    add(0, element);
  }

  public E get(int index)
  {

    Node<E> current = mHead;
    for (int i = 0; i < index; i++) {
      current = current.mNext;
    }
    return current.mData;
  }

  public E set(int index, E element)
  {
    checkIndex(index, 0, mSize-1);
    Node<E> current = mHead;
    for (int i = 0; i < index; i++)
      current = current.mNext;
    E data = (E) current.mData;
    current.mData = element;
    return data;
  }

  private void checkIndex(int index, int min, int max)
  {
    if (index < min || index > max)
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + mSize);
  }

  @Override
  public String toString()
  {
    if (mSize == 0)
      return "[]";

    Node<E> current = mHead;
    StringBuilder sb = new StringBuilder("[").append(current.mData);
    while (current.mNext != null)
    {
      current = current.mNext;
      sb.append(", ").append(current.mData);
    }
    return sb.append("]").toString();
  }

  public void clear()
  {
    mHead = null; mSize = 0;
  }

  public int indexOf(Object element)
  {
    Node<E> current = mHead;
    for (int i = 0; i < mSize; i++) {
      if ((element == null && current.mData == null) || (element != null && element.equals(current.mData)))
        return i;
      current = current.mNext;
    }
    return -1;
  }

  public boolean contains(Object element)
  {
    return indexOf(element) != -1;
  }

  public E remove(int index)
  {
    checkIndex(index, 0, mSize-1);
    
    Node<E> current = mHead;
    if (index == 0) {
      mHead = mHead.mNext;
      return current.mData;
    }

    for (int i = 0; i < index-1; i++) 
      current = current.mNext;
    
    E data = current.mNext.mData;
    current.mNext = current.mNext.mNext;
	mSize--;
    return data;
  }

  public boolean remove(E element)
  {
    int index = indexOf(element);
    if (index == -1)
      return false;
    else
    {
      remove(index);
      return true;
    }

  }


  private class Node<E>
  {
    private E mData;
    private Node<E> mNext;

    public Node(E data, Node<E> next)
    {
      mData = data;
      mNext = next;
    }
    public Node(E data)
    {
      this(data, null);
    }

    @Override
    public String toString()
    {
      return "| " + mData + " | " + Integer.toHexString(mNext.hashCode()) + " |";
    }
  }


  public class ListIterator
  {
    // 3 fields
    private Node<E> mPrev;
    private Node<E> mNext;
    private int mCursor;

    public ListIterator()
    {
      mPrev = null;
      mCursor = 0;
      mNext = mHead; // Accessing outer class
    }

    // Has next
    public boolean hasNext()
    {
      return mNext != null; // Checks if next is empty
    }

    // Has previous
    public boolean hasPrevious()
    {
      return mPrev != null;
    }

    // next
    public E next()
    {
      mCursor++;
      mPrev = mNext;
      mNext = mNext.mNext;
      return mPrev.mData;
    }
    // Previous
    public E Previous()
    {
      mCursor--;
      mNext = mPrev;
      Node<E> temp = mHead;
      for (int i = 0; i < mCursor-1; i++) {
        temp = temp.mNext;
      }
      mPrev = temp;
      return mNext.mData;
    }

    public int PreviousIndex()
    {
      return mCursor-1;
    }

    public int NextIndex()
    {
      return mCursor+1;
    }

    public void add(E element)
    {
      LinkedList.this.add(element); //Must reference the outer class, LinkedList in order to use this
    }

    public void remove()
    {
      LinkedList.this.remove(mCursor);
    }

    public void set(E element)
    {
      LinkedList.this.set(mCursor, element);
    }
  }


}
