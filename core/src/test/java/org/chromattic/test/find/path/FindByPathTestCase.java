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

package org.chromattic.test.find.path;

import org.chromattic.test.AbstractTestCase;
import org.chromattic.test.find.TFI_A;
import org.chromattic.api.ChromatticSession;

import javax.jcr.RepositoryException;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class FindByPathTestCase extends AbstractTestCase {

  protected void createDomain() {
    addClass(TFI_A.class);
  }

  public void testSessionFind() throws RepositoryException {
    ChromatticSession session = login();
    TFI_A a = session.insert(TFI_A.class, "tfi_a_a");
    TFI_A b = session.findByPath(TFI_A.class, "tfi_a_a");
    assertSame(a, b);
  }

  public void testCCE() throws Exception {
    ChromatticSession session = login();
    TFI_A a = session.insert(TFI_A.class, "tfi_a_a");
    try {
      session.findByPath(String.class, "tfi_a_a");
      fail();
    }
    catch (ClassCastException e) {
    }
  }

  public void testNotFound() throws RepositoryException {
    ChromatticSession session = login();
    TFI_A a = session.findByPath(TFI_A.class, "foo");
    assertNull(a);
  }

  public void testNPE() throws Exception {
    ChromatticSession session = login();
    session.insert(TFI_A.class, "tfi_a_a");
    try {
      session.findByPath(TFI_A.class, null);
      fail();
    }
    catch (NullPointerException e) {
    }
    try {
      session.findByPath(null, "tfi_a_a");
      fail();
    }
    catch (NullPointerException e) {
    }
  }
}