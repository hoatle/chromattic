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

package org.chromattic.test.common;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.chromattic.common.CompoundIterator;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class CompoundIteratorTestCase extends TestCase {

  public void testIterators() {
    Iterator<Integer> iterator = new CompoundIterator<Integer>(Arrays.asList(0).iterator(), Arrays.asList(1, 2).iterator());
    assertTrue(iterator.hasNext());
    assertEquals((Integer)0, iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals((Integer)1, iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals((Integer)2, iterator.next());
    assertFalse(iterator.hasNext());
    try {
      iterator.next();
      fail();
    }
    catch (NoSuchElementException e) {
    }
  }
}
