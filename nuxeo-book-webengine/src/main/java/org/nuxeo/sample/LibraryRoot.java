/*
 * (C) Copyright ${year} Nuxeo SA (http://nuxeo.com/) and contributors.
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

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jackson.map.ObjectMapper;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.impl.blob.StringBlob;
import org.nuxeo.ecm.webengine.forms.FormData;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.exceptions.WebResourceNotFoundException;
import org.nuxeo.ecm.webengine.model.impl.ModuleRoot;
import org.nuxeo.runtime.api.Framework;

/**
 * The root entry for the WebEngine module.
 *
 * @author dmetzler
 */
@Path("/library")
@Produces("text/html;charset=UTF-8")
@WebObject(type = "LibraryRoot")
public class LibraryRoot extends ModuleRoot {

    @GET
    public Object doGet() {
        return getView("index");
    }

    @GET
    @Produces("application/json;charset=UTF-8")
    public Object doGetJson() {
        List<BookLibrary> allLibraries = Framework.getLocalService(LibraryService.class)
                                                  .getAllLibraries(getContext().getCoreSession());
        return toJsonBlob(allLibraries);

    }

    protected Blob toJsonBlob(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, object);
            return new StringBlob(writer.toString());
        } catch (IOException e) {
            return new StringBlob("");
        }
    }

    public List<BookLibrary> getLibraries() {
        LibraryService ls = Framework.getLocalService(LibraryService.class);
        return ls.getAllLibraries(getContext().getCoreSession());
    }

    @POST
    public Object doCreateLibrary() {
        FormData form = getContext().getForm();
        LibraryService ls = Framework.getLocalService(LibraryService.class);
        ls.createLibrary(form.getString("title"), getContext().getCoreSession());
        return redirect(getPath());

    }

    @Path("{libraryTitle}")
    public Object getLibrary(@PathParam("libraryTitle") String libraryTitle) {
        LibraryService ls = Framework.getLocalService(LibraryService.class);
        BookLibrary library = ls.getLibrary(libraryTitle, getContext().getCoreSession());

        if (library == null) {
            return new WebResourceNotFoundException("Library not found");
        }

        return newObject("Library", library.getDoc());

    }

}
