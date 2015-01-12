/*
 * Copyright (C) 2013 DASISH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package eu.dasish.annotation.backend.rest;

import eu.dasish.annotation.backend.BackendConstants;
import eu.dasish.annotation.backend.Helpers;
import eu.dasish.annotation.backend.NotInDataBaseException;
import eu.dasish.annotation.backend.Resource;
import eu.dasish.annotation.schema.AnnotationInfo;
import eu.dasish.annotation.schema.AnnotationInfoList;
import eu.dasish.annotation.schema.ObjectFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author olhsha
 */
@Component
@Path("/debug")
public class DebugResource extends ResourceResource {

    public static final String developer = "developer";
    
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("uuid")
    public String generateUUID() throws IOException {
       return (Helpers.generateUUID()).toString();
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("annotations")
    @Transactional(readOnly = true)
    public JAXBElement<AnnotationInfoList> getAllAnnotations() throws IOException {
        Number remotePrincipalID = this.getPrincipalID();
        if (remotePrincipalID == null) {
            return new ObjectFactory().createAnnotationInfoList(new AnnotationInfoList());
        }
        String typeOfAccount = dbDispatcher.getTypeOfPrincipalAccount(remotePrincipalID);
        if (typeOfAccount.equals(admin) || typeOfAccount.equals(developer)) {
            final AnnotationInfoList annotationInfoList = dbDispatcher.getAllAnnotationInfos();
            return new ObjectFactory().createAnnotationInfoList(annotationInfoList);
        } else {
            this.DEVELOPER_RIGHTS_EXPECTED();
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return new ObjectFactory().createAnnotationInfoList(new AnnotationInfoList());
        }
    }
    
    

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/logDatabase/{n}")
    @Transactional(readOnly = true)
    public String getDasishBackendLog(@PathParam("n") int n) throws IOException {
        Number remotePrincipalID = this.getPrincipalID();
        if (remotePrincipalID == null) {
            return " ";
        }
        String typeOfAccount = dbDispatcher.getTypeOfPrincipalAccount(remotePrincipalID);
        if (typeOfAccount.equals(admin) || typeOfAccount.equals(developer)) {
            return logFile("eu.dasish.annotation.backend.logDatabaseLocation", n);
        } else {
            this.DEVELOPER_RIGHTS_EXPECTED();
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return "Coucou.";
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/remoteID")
    @Transactional(readOnly = true)
    public String getLoggedInRemoteID() {
        return (httpServletRequest.getRemoteUser() != null) ? httpServletRequest.getRemoteUser() : "Null";
    }

    /////
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/logServer/{n}")
    @Transactional(readOnly = true)
    public String getDasishServerLog(@PathParam("n") int n) throws IOException {
        Number remotePrincipalID = this.getPrincipalID();
        if (remotePrincipalID == null) {
            return " ";
        }
        String typeOfAccount = dbDispatcher.getTypeOfPrincipalAccount(remotePrincipalID);
        if (typeOfAccount.equals(admin) || typeOfAccount.equals(developer)) {
            return logFile("eu.dasish.annotation.backend.logServerLocation", n);
        } else {
            this.DEVELOPER_RIGHTS_EXPECTED();
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return "Coucou.";
        }
    }

    //////////////////////////////////
    @PUT
    @Produces(MediaType.TEXT_XML)
    @Path("/account/{principalId}/make/{account}")
    @Transactional(readOnly = true)
    public String updatePrincipalsAccount(@PathParam("principalId") String principalId, @PathParam("account") String account) throws IOException {
        Number remotePrincipalID = this.getPrincipalID();
        if (remotePrincipalID == null) {
            return " ";
        }
        String typeOfAccount = dbDispatcher.getTypeOfPrincipalAccount(remotePrincipalID);
        if (typeOfAccount.equals(admin)) {
            try {
                final boolean update = dbDispatcher.updateAccount(UUID.fromString(principalId), account);
                return (update ? "The account is updated" : "The account is not updated, see the log.");
            } catch (NotInDataBaseException e) {
                httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
                return e.toString();
            }
        } else {
            this.ADMIN_RIGHTS_EXPECTED();
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return "Coucou.";
        }

    }
    
    //////////////////////////////////
    @PUT
    @Produces(MediaType.TEXT_XML)
    @Path("/resource/{resource}/{oldId: " + BackendConstants.regExpIdentifier + "}/newid/{newId:" + BackendConstants.regExpIdentifier + "}")
    public String updateResourceIdentifier(@PathParam("resource") String resource, @PathParam("oldId") String oldExternalId, @PathParam("newId") String newExternalId) throws IOException {
        Number remotePrincipalID = this.getPrincipalID();
        if (remotePrincipalID == null) {
            return "null inlogged principal";
        }
        String typeOfAccount = dbDispatcher.getTypeOfPrincipalAccount(remotePrincipalID);
        if (typeOfAccount.equals(admin)) {
            try {
                final boolean update = dbDispatcher.updateResourceIdentifier(Resource.valueOf(resource), UUID.fromString(oldExternalId), UUID.fromString(newExternalId));
                return (update ? "The identifier is updated" : "The account is not updated, see the log.");
            } catch (NotInDataBaseException e) {
                httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
                return e.toString();
            }
        } else {
            this.ADMIN_RIGHTS_EXPECTED();
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
            return "Dooooeeeii!!";
        }

    }

    ///////////////////////////////////////////////////
    private String logFile(String location, int n) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(context.getInitParameter(location)));
        List<String> lines = new ArrayList<String>();
        StringBuilder result = new StringBuilder();
        int i = 0;
        String line;
        while ((line = read.readLine()) != null) {
            lines.add(line);
            i++;
        }
        // want to read the last n rows, i.e. the rows (i-1), (i-1-1),...,(i-1-(n-1))
        int last = (i > n) ? (i - n) : 0;
        for (int j = i - 1; j >= last; j--) {
            result.append(lines.get(j)).append("\n");
        }
        return result.toString();
    }
    
    private void DEVELOPER_RIGHTS_EXPECTED() throws IOException {
        loggerServer.debug("The request can be performed only by the principal with the developer's or admin rights. The logged in principal does not have either developer's or admin rights.");
    }
}