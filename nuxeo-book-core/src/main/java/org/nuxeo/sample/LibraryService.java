/*
 * (C) Copyright 2013 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     dmetzler
 */
package org.nuxeo.sample;

import java.util.List;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;

/**
 * @author dmetzler
 *
 */
public interface LibraryService {

    /**
     * @return
     */
    List<BookLibrary> getAllLibraries(CoreSession session) throws ClientException;

    /**
     * @param libraryTitle
     * @param session
     * @return
     * @throws ClientException
     */
    BookLibrary createLibrary(String libraryTitle, CoreSession session) throws ClientException;

}
