/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shopscanmanager;

/*
 * BoxLayoutType.java
 * Copyright (C) 1999 dog <dog@dog.net.uk>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * You may retrieve the latest version of this library from
 * http://www.dog.net.uk/knife/
 */


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

/**
 * A box layout is used to lay out components in a container.
 * It will arranges the components either horizontally or vertically.
 * In a horizontal box layout, each component is expanded to the height of the container
 * and the components are laid out from left to right.
 * In a vertical box layout, each component is expanded to the width of the container
 * and the components are laid out from top to bottom.
 *
 * @author dog@dog.net.uk
 * @version 1.0.1
 */
public class BoxLayoutType
	implements LayoutManager 
{

	/**
	 * The horizontal orientation.
	 */
	public static final int HORIZONTAL = 0;

	/**
	 * The vertical orientation.
	 */
	public static final int VERTICAL = 1;
	
	/**
	 * The orientation of the layout.
	 */
	protected int orientation;
	
	int hgap;
	int vgap;

	/**
	 * Constructs a new vertical box layout with the default 5-unit horizontal and vertical gap.
	 */
	public BoxLayoutType() 
	{
		this(VERTICAL, 5, 5);
	}

	/**
	 * Constructs a new box layout with the specified orientation and the
	 * default 5-unit horizontal and vertical gap.
	 * @param orientation the orientation of the layout
	 */
	public BoxLayoutType(int orientation) 
	{
		this(orientation, 5, 5);
	}

	/**
	 * Constructs a new layout with the specified orientation and gap values.
	 * @param orientation the orientation of the layout
	 * @param hgap the horizontal gap variable
	 * @param vgap the vertical gap variable
	 */
	public BoxLayoutType(int orientation, int hgap, int vgap) 
	{
		if (orientation!=HORIZONTAL && orientation!=VERTICAL)
			throw new IllegalArgumentException("illegal orientation: "+orientation);
		if (hgap<0)
			throw new IllegalArgumentException("horizontal gap is less than 0");
		if (vgap<0)
			throw new IllegalArgumentException("vertical gap is less than 0");
		this.orientation = orientation;
		this.hgap = hgap;
		this.vgap = vgap;
	}

	/**
	 * Returns the orientation of this layout (HORIZONTAL or VERTICAL).
	 */
	public int getOrientation() 
	{
		return orientation;
	}
	
	/**
	 * Sets the orientation value for this layout.
	 * @param orientation the orientation value (HORIZONTAL or VERTICAL).
	 */
	public void setOrientation(int orientation) 
	{
		if (orientation!=HORIZONTAL && orientation!=VERTICAL)
			throw new IllegalArgumentException("illegal orientation: "+orientation);
		this.orientation = orientation;
	}

	/**
	 * Returns the horizontal gap between components.
	 */
	public int getHgap() 
	{
		return hgap;
	}
	
	/**
	 * Sets the horizontal gap between components.
	 * @param hgap the horizontal gap between components
	 */
	public void setHgap(int hgap) 
	{
		if (hgap<0)
			throw new IllegalArgumentException("horizontal gap is less than 0");
		this.hgap = hgap;
	}
	
	/**
	 * Returns the vertical gap between components.
	 */
	public int getVgap() 
	{
		return vgap;
	}
	
	/**
	 * Sets the vertical gap between components.
	 * @param vgap the vertical gap between components
	 */
	public void setVgap(int vgap) 
	{
		if (vgap<0)
			throw new IllegalArgumentException("vertical gap is less than 0");
		this.vgap = vgap;
	}

	/**
	 * Adds the specified component to the layout. Not used by this class.
	 * @param name the name of the component
	 * @param comp the the component to be added
	 */
	public void addLayoutComponent(String name, Component comp) 
	{
	}

	/**
	 * Removes the specified component from the layout. Not used by this class.
	 * @param comp the component to remove
	 */
	public void removeLayoutComponent(Component comp) 
	{
	}

	/**
	 * Returns the preferred dimensions for this layout given the components
	 * in the specified target container.
	 * @param target the component which needs to be laid out
	 * @see Container
	 * @see #minimumLayoutSize
	 */
	public Dimension preferredLayoutSize(Container target) 
	{
		Dimension dim = new Dimension(0, 0);
		int nmembers = target.getComponentCount();
		for (int i=0; i<nmembers; i++) 
		{
			Component m = target.getComponent(i);
			if (m.isVisible()) 
			{
				Dimension d = m.getPreferredSize();
				switch (orientation) 
				{
				  case HORIZONTAL:
					dim.height = Math.max(dim.height, d.height);
					if (i>1)
						dim.width += hgap;
					dim.width += d.width;
					break;
				  case VERTICAL:
					dim.width = Math.max(dim.width, d.width);
					if (i>1)
						dim.height += vgap;
					dim.height += d.height;
					break;
				}
			}
		}
		Insets insets = target.getInsets();
		dim.width += insets.left + insets.right + hgap*2;
		dim.height += insets.top + insets.bottom + vgap*2;
		return dim;
	}

	/**
	 * Returns the minimum dimensions needed to layout the components
	 * contained in the specified target container.
	 * @param target the component which needs to be laid out 
	 * @see #preferredLayoutSize
	 */
	public Dimension minimumLayoutSize(Container target) 
	{
		Dimension dim = new Dimension(0, 0);
		int nmembers = target.getComponentCount();
		for (int i=0; i<nmembers; i++) 
		{
			Component m = target.getComponent(i);
			if (m.isVisible()) 
			{
				Dimension d = m.getMinimumSize();
				dim.width = Math.max(dim.width, d.width);
				if (i>0)
					dim.height += vgap;
				dim.height += d.height;
			}
		}
		Insets insets = target.getInsets();
		dim.width += insets.left + insets.right + hgap*2;
		dim.height += insets.top + insets.bottom + vgap*2;
		return dim;
	}

  public boolean dog=false;
  public void layoutContainer(Container target){
    if (dog){ dogLayoutContainer(target); }else{ daveLayoutContainer(target); }
  }

	/**
	 * <p>Lays out the container.
   * <p>The original dog.gui code set all components to the same height. This can be achieved
   * with a GridLayout, so I've altered it to give the (to me at least) more useful behaviour of
   * setting each component to its own preferred height (when VERTICAL) or width (when HORIZONTAL)
   * and spanning the full width/height of the container in the other direction. DC april 2002
	 * @param target the specified component being laid out.
	 * @see Container
	 */
	public void daveLayoutContainer(Container target) 
	{
		Insets insets = target.getInsets();
		Rectangle bounds = target.getBounds();
		int nmembers = target.getComponentCount();
		int y = insets.top, x = insets.left;
		Dimension md = new Dimension(0, 0);

    //get the size of the widest and the highest component
		for (int i=0; i<nmembers; i++) 
		{
			Component m = target.getComponent(i);
			if (m.isVisible()) 
			{
				Dimension d = m.getPreferredSize();
				switch (orientation) 
				{
				  case HORIZONTAL:
					md.width = (d.width>md.width) ? d.width : md.width;
					break;
				  case VERTICAL:
					md.height = (d.height>md.height) ? d.height : md.height;
					break;
				}
			}
		}
    //set the 'other' dimension of the container
		switch (orientation) 
		{
		  case HORIZONTAL:
			md.height = bounds.height-(insets.top+insets.bottom);
			break;
		  case VERTICAL:
			md.width = bounds.width-(insets.left+insets.right);
			break;
		}
		for (int i=0; i<nmembers; i++) 
		{
			Component m = target.getComponent(i);
			if (m.isVisible()) 
			{
				Dimension d = m.getPreferredSize();
				switch (orientation) 
				{
				  case HORIZONTAL:
  				m.setBounds(x, y, d.width, md.height);
					x += d.width+hgap;
					break;
				  case VERTICAL:
  				m.setBounds(x, y, md.width, d.height);
					y += d.height+vgap;
					break;
				}
//System.out.println("Component "+m+"\n  pref size = "+d+"\n  bounds = "+m.getBounds());
			}
		}
	}

///* -- old code ---
	public void dogLayoutContainer(Container target) 
	{
		Insets insets = target.getInsets();
		Rectangle bounds = target.getBounds();
		int nmembers = target.getComponentCount();
		int y = insets.top, x = insets.left;
		Dimension md = new Dimension(0, 0);

		for (int i=0; i<nmembers; i++) 
		{
			Component m = target.getComponent(i);
			if (m.isVisible()) 
			{
				Dimension d = m.getPreferredSize();
				switch (orientation) 
				{
				  case HORIZONTAL:
					md.width = (d.width>md.width) ? d.width : md.width;
					break;
				  case VERTICAL:
					md.height = (d.height>md.height) ? d.height : md.height;
					break;
				}
			}
		}
		switch (orientation) 
		{
		  case HORIZONTAL:
			md.height = bounds.height-(insets.top+insets.bottom);
			break;
		  case VERTICAL:
			md.width = bounds.width-(insets.left+insets.right);
			break;
		}
		for (int i=0; i<nmembers; i++) 
		{
			Component m = target.getComponent(i);
			if (m.isVisible()) 
			{
				m.setBounds(x, y, md.width, md.height);
				switch (orientation) 
				{
				  case HORIZONTAL:
					x += md.width+hgap;
					break;
				  case VERTICAL:
					y += md.height+vgap;
					break;
				}
//System.out.println("Component "+m+"\n  pref size = "+m.getPreferredSize()+"\n  bounds = "+m.getBounds());
			}
		}
	}
//--- end of old code ---*/	

	/**
	 * Returns the string representation of this layout's values.
	 */
	public String toString() 
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClass().getName());
		buffer.append("[hgap=");
		buffer.append(hgap);
		buffer.append(",vgap=");
		buffer.append(vgap);
		switch (orientation) 
		{
		  case VERTICAL:
			buffer.append(",orientation=vertical");
			break;
		  case HORIZONTAL: 
			buffer.append(",orientation=horizontal"); 
			break;
		}
		buffer.append("]");
		return buffer.toString();
	}
}
