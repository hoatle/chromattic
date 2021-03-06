/*
 * Copyright (C) 2010 eXo Platform SAS.
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

package org.chromattic.dataobject.runtime;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.chromattic.api.ChromatticException;
import org.chromattic.api.ChromatticSession;
import org.chromattic.api.Status;
import org.chromattic.api.event.EventListener;
import org.chromattic.api.query.QueryBuilder;
import org.exoplatform.services.security.ConversationState;

import javax.jcr.Node;
import javax.jcr.Session;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class DataObjectChromatticSession implements ChromatticSession {

  /** Our provider. */
  private final ChromatticSessionProvider provider;

  /** The effective session. */
  private ChromatticSession effective;

  /** . */
  ChromatticBuilder builder;

  /** . */
  private static final ThreadLocal<DataObjectChromatticSession> current = new ThreadLocal<DataObjectChromatticSession>();

  DataObjectChromatticSession(ChromatticSessionProvider provider) {
    this.provider = provider;
  }

  private ChromatticSession safeGet() throws IllegalStateException {
    if (effective == null) {
      if (builder == null) {
        throw new IllegalStateException("Chromattic session proxy is currently not associated");
      }

      //
      ConversationState cs = ConversationState.getCurrent();
      String userId = cs.getIdentity().getUserId();
      String rootNodePath = provider.rootNodePath + userId;
      String rootNodeType = provider.rootNodeType;

      //
      ChromatticBuilder.Configuration config = new ChromatticBuilder.Configuration(builder.getConfiguration());
      config.setOptionValue(ChromatticBuilder.ROOT_NODE_PATH, rootNodePath, true);
      if (rootNodeType != null) {
        config.setOptionValue(ChromatticBuilder.ROOT_NODE_TYPE, rootNodeType, true);
      }
      Chromattic chromattic = builder.build(config);

      //
      effective = chromattic.openSession();
      current.set(this);
    }
    return effective;
  }

  static void cleanup() {
    DataObjectChromatticSession proxy = current.get();
    if (current != null) {
      proxy.effective.close();
    }
  }

  public <O> O create(Class<O> clazz) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().create(clazz);
  }

  public <O> O create(Class<O> clazz, String name) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().create(clazz, name);
  }

  public <O> O insert(Class<O> clazz, String name) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().insert(clazz, name);
  }

  public <O> O insert(Object parent, Class<O> clazz, String name) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().insert(parent, clazz, name);
  }

  public <O> O insert(Object parent, Class<O> clazz, String prefix, String localName) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().insert(parent, clazz, prefix, localName);
  }

  public <O> O insert(Class<O> clazz, String prefix, String localName) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().insert(clazz, prefix, localName);
  }

  public String persist(Object o, String name) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().persist(o, name);
  }

  public String persist(Object parent, Object child) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().persist(parent, child);
  }

  public String persist(Object o) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().persist(o);
  }

  public String persist(Object parent, Object o, String name) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().persist(parent, o, name);
  }

  public String persist(Object o, String prefix, String localName) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().persist(o, prefix, localName);
  }

  public String persist(Object parent, Object o, String prefix, String localName) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().persist(parent, o, prefix, localName);
  }

  public <O> O copy(Object parent, O o, String name) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().copy(parent, o, name);
  }

  public <O> O copy(O o, String name) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().copy(o, name);
  }

  public <O> O findByPath(Object origin, Class<O> clazz, String relPath) throws IllegalArgumentException, NullPointerException, ClassCastException, ChromatticException {
    return safeGet().findByPath(origin, clazz, relPath);
  }

  public <O> O findByPath(Class<O> clazz, String relPath) throws NullPointerException, ClassCastException, ChromatticException {
    return safeGet().findByPath(clazz, relPath);
  }

  public <O> O findByPath(Class<O> clazz, String path, boolean absolute) throws NullPointerException, ClassCastException, ChromatticException {
    return safeGet().findByPath(clazz, path, absolute);
  }

  public <O> O findByNode(Class<O> clazz, Node node) throws NullPointerException, ClassCastException, ChromatticException {
    return safeGet().findByNode(clazz, node);
  }

  public <O> O findById(Class<O> clazz, String id) throws NullPointerException, ClassCastException, ChromatticException {
    return safeGet().findById(clazz, id);
  }

  public <O> QueryBuilder<O> createQueryBuilder(Class<O> fromClass) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().createQueryBuilder(fromClass);
  }

  public void remove(Object o) throws NullPointerException, IllegalArgumentException, ChromatticException {
    safeGet().remove(o);
  }

  public Status getStatus(Object o) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().getStatus(o);
  }

  public String getId(Object o) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().getId(o);
  }

  public String getName(Object o) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().getName(o);
  }

  public void setName(Object o, String name) throws NullPointerException, IllegalArgumentException, ChromatticException {
    safeGet().setName(o, name);
  }

  public String getPath(Object o) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().getPath(o);
  }

  public <E> E getEmbedded(Object o, Class<E> embeddedType) throws NullPointerException, IllegalArgumentException, ChromatticException {
    return safeGet().getEmbedded(o, embeddedType);
  }

  public <E> void setEmbedded(Object o, Class<E> embeddedType, E embedded) throws NullPointerException, IllegalArgumentException, ChromatticException {
    safeGet().setEmbedded(o, embeddedType, embedded);
  }

  public void addEventListener(EventListener listener) throws NullPointerException {
    safeGet().addEventListener(listener);
  }

  public void save() throws ChromatticException {
    safeGet().save();
  }

  public void close() {
    safeGet().close();
  }

  public boolean isClosed() {
    return safeGet().isClosed();
  }

  public Session getJCRSession() {
    return safeGet().getJCRSession();
  }
}
