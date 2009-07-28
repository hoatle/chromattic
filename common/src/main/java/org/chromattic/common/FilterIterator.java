/*
 * Copyright (C) 2003-2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.chromattic.common;

import java.util.Iterator;

/**
 * A convenient subclass for delegating the filtering function.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class FilterIterator<E, I> extends AbstractFilterIterator<E, I> {

  /** . */
  private final IteratorFilter<E, I> filter;

  /**
   * Creates a new filter iterator.
   *
   * @param iterator the iterator
   * @param filter the filter
   * @throws NullPointerException if any argument is null
   */
  public FilterIterator(Iterator<I> iterator, IteratorFilter<E, I> filter) throws NullPointerException {
    super(iterator);

    //
    if (filter == null) {
      throw new NullPointerException();
    }

    //
    this.filter = filter;
  }

  protected E adapt(I internal) {
    return filter.adapt(internal);
  }
}
