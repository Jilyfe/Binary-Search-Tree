package org.fauberteau.rbtree;

import java.util.ArrayDeque;
import java.util.Random;

/**
 * Implement a Binary Search Tree (BST) data structure. It is not thread-safe
 * 
 * @author Fr�d�ric Fauberteau
 *
 */
public class BinarySearchTree<E extends Comparable<E>> implements Runnable
{
	private BinarySearchNode<E> root = null;
	private int nbThread;
	private int nbNode;
	
	BinarySearchTree(int nbThread, int nbNode)
	{
		this.nbNode = nbNode;
		this.nbThread = nbThread;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run()
	{
		int i, nbNode;
		Random rand = new Random();
		nbNode = this.nbNode;
		for(i=0; i<nbNode/nbThread; i++)
		{
			while(this.add((E)(Comparable)(rand.nextInt(nbNode) + 1)) == false);
		}
	}
  
	/**
	* Add an element in the tree if it is not already present.
	* 
	* @param e
	*          the element to add in the tree
	* @return true if the element has been correctly added and false if it is
	*         already present
	 * @throws InterruptedException 
	*/
	public boolean add(E e)
	{
		if(isRootSet(e) == false)
		{
			BinarySearchNode<E> y = null;
			BinarySearchNode<E> x = getRoot();
			while (x != null)
			{
				try
				{
					x.lock();
				}
				catch(InterruptedException exception)
				{
					exception.printStackTrace();
				}
				finally
				{
					y = x;
					if (x.getKey().compareTo(e) == 0)
					{
						y.unlock();
						return false;
					}
					else if(x.getKey().compareTo(e) > 0)
					{
						x = x.getRightChild();
						if(x == null)
						{
							y.setRightChild(new BinarySearchNode<>(e));
						}
						y.unlock();
					}
					else
					{
						x = x.getLeftChild();
						if(x == null)
						{
							y.setLeftChild(new BinarySearchNode<>(e));
						}
						y.unlock();
					}
				}
			}
		}
		return true;
	}
	
	private synchronized boolean isRootSet(E e)
	{
		if(root == null)
		{
			root = new BinarySearchNode<>(e);
			return true;
		}
		return false;
	}

	private BinarySearchNode<E> getRoot()
	{
		return root;
	}
  
	public int getNbThread()
	{
		return nbThread;
	}
  
	public String toDOT(String name)
	{
		ArrayDeque<BinarySearchNode<E>> queue = new ArrayDeque<>();
		StringBuilder sb = new StringBuilder();
		if (getRoot() != null)
		{
			sb.append("graph ").append(name).append(" {\n");
			queue.offerFirst(getRoot());
			while (!queue.isEmpty())
			{
				BinarySearchNode<E> node = queue.pollLast();
				if (node.hasRightChild())
				{
					BinarySearchNode<E> right = node.getRightChild();
					sb.append(node).append(" -- ").append(right).append("\n");
					queue.offerFirst(right);
				}
				if (node.hasLeftChild())
				{
					BinarySearchNode<E> left = node.getLeftChild();
					sb.append(node).append(" -- ").append(left).append(";\n");
					queue.offerFirst(left);
				}
			}
			sb.append('}');
		}
		return sb.toString();
	}
  
	private static class BinarySearchNode<E extends Comparable<E>>
	{
    
		private final E key;
 
		private BinarySearchNode<E> left;
    
		private BinarySearchNode<E> right;
		
		Lock locky;
		
		private BinarySearchNode(E key)
		{
			this.key = key;
			locky = new Lock();
		}

		private void lock() throws InterruptedException
		{
			locky.lock();
		}

		private void unlock()
		{
			locky.unlock();
		}
    
		private E getKey()
		{
			return key;
		}
    
		private BinarySearchNode<E> getLeftChild()
		{
			return left;
		}
    
		private BinarySearchNode<E> getRightChild()
		{
			return right;
		}
    
		private boolean hasLeftChild()
		{
			return getLeftChild() == null ? false : true;
		}
    
		private boolean hasRightChild()
		{
			return getRightChild() == null ? false : true;
		}
   
		private void setLeftChild(BinarySearchNode<E> node)
		{
			left = node;
		}
    
		private void setRightChild(BinarySearchNode<E> node)
		{
			right = node;
		}
    
		@Override
		public String toString()
		{
			return getKey().toString();
		}
	}
}
