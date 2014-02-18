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

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

/**
 *
 * @author olhsha
 */
public class VerboseOutput {
    
    private HttpServletResponse httpServletResponse;
    private  Logger logger;

    private class MessageStatus{
        String message;
        int status;
        
        public MessageStatus(String msg, int status){
            message = msg;
            this.status = status;
        }
    }
    
    public VerboseOutput(HttpServletResponse httpServletResponse, Logger logger) {
        this.httpServletResponse = httpServletResponse;
        this.logger = logger;
    }
    
    
    
    private MessageStatus _FORBIDDEN_NOTEBOOK_READING(String identifier) {
        return new MessageStatus(" The logged-in principal cannot read the notebook with the identifier " + identifier, HttpServletResponse.SC_FORBIDDEN);
    }
    
    private MessageStatus _FORBIDDEN_NOTEBOOK_WRITING(String identifier) {
        return new MessageStatus(" The logged-in principal cannot write in the notebook with the identifier " + identifier, HttpServletResponse.SC_FORBIDDEN);
    }
    
    private MessageStatus  _FORBIDDEN_ANNOTATION_READING(String identifier) {
        return new MessageStatus(" The logged-in principal cannot read the annotation with the identifier " + identifier, HttpServletResponse.SC_FORBIDDEN);
    }
    
    private MessageStatus  _FORBIDDEN_ANNOTATION_WRITING(String identifier) {
        return new MessageStatus(" The logged-in principal cannot write in the annotation with the identifier " + identifier, HttpServletResponse.SC_FORBIDDEN);
    }
    
    private MessageStatus  _FORBIDDEN_PERMISSION_CHANGING(String identifier) {
        return new MessageStatus(" The logged-in principal cannot change the permission of the resource with with the identifier " + identifier+". Only the owner of the resource is allowed to chnange permissions.",HttpServletResponse.SC_FORBIDDEN);
    }

    private MessageStatus  _ILLEGAL_UUID(String identifier) {
        return new MessageStatus("The string '" + identifier + "' is not a valid UUID.", HttpServletResponse.SC_BAD_REQUEST);
    }
    

    private MessageStatus  _REMOTE_PRINCIPAL_NOT_FOUND(String remoteID) {
        return new MessageStatus("The loggedinprincipal with the remote ID " + remoteID + " is not found in the database.", HttpServletResponse.SC_NOT_FOUND);
    }
    
    private MessageStatus  resourceNotFound(String externalIdentifier, String resourceType) {
        return new MessageStatus("A(n) " + resourceType + " with the indentifier " + externalIdentifier + " is not found in the database.", HttpServletResponse.SC_NOT_FOUND);
    }
    
     private MessageStatus  _PRINCIPAL_NOT_FOUND(String externalIdentifier) {
        return resourceNotFound(externalIdentifier, "principal");
    }

    private MessageStatus  _ANNOTATION_NOT_FOUND(String externalIdentifier) {
        return resourceNotFound(externalIdentifier, "annotation");
    }

    private MessageStatus  _NOTEBOOK_NOT_FOUND(String externalIdentifier) {
        return resourceNotFound(externalIdentifier, "notebook");
    }

    private MessageStatus  _TARGET_NOT_FOUND(String externalIdentifier) {
        return resourceNotFound(externalIdentifier, "target");
    }

    private MessageStatus  _CACHED_REPRESENTATION_NOT_FOUND(String externalIdentifier) {
        return resourceNotFound(externalIdentifier, "cached representation");
    }

    private MessageStatus  _INVALID_PERMISSION_MODE(String permissionMode) {
        return new MessageStatus(permissionMode + " is an invalid permission value, which must be either owner, or reader, or writer.", HttpServletResponse.SC_BAD_REQUEST);
    }
    
    private MessageStatus _IDENTIFIER_MISMATCH(String identifier){
        return new MessageStatus("Wrong request: the annotation identifier   " + identifier + " and the annotation ID from the request body do not match.", HttpServletResponse.SC_BAD_REQUEST);
    }
    
    private MessageStatus _ADMIN_RIGHTS_EXPECTED(){
        return new MessageStatus("The request can be performed only by the principal with the admin rights. The logged in principal does not have admin rights.", HttpServletResponse.SC_FORBIDDEN);
    }

     private MessageStatus _DEVELOPER_RIGHTS_EXPECTED(){
        return new MessageStatus("The request can be performed only by the principal with the developer's or admin rights. The logged in principal does not have either developer's or admin rights.", HttpServletResponse.SC_FORBIDDEN);
    }
     
     private MessageStatus  _PRINCIPAL_NOT_FOUND_BY_INFO(String email) {
        return new MessageStatus("The principal with the info (e-mail) "+email+" is not found in the database.", HttpServletResponse.SC_NOT_FOUND);
    }
     
    private MessageStatus  _PRINCIPAL_IS_NOT_ADDED_TO_DB() {
        return new MessageStatus("The principal is not added the database, probably becuase another principal with the same e-mail already exists in the data base.", HttpServletResponse.SC_BAD_REQUEST);
    } 
    
    private MessageStatus  _ACCOUNT_IS_NOT_UPDATED() {
        return new MessageStatus("The account is not updated", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    } 
    
    private void sendMessage(MessageStatus msg) throws IOException {
        logger.debug(msg.status + ": " + msg.message);
        httpServletResponse.sendError(msg.status, msg.message);
    }
    
    ///////////////////////////////
    
    public void FORBIDDEN_NOTEBOOK_READING(String identifier) throws IOException{
        this.sendMessage(this._FORBIDDEN_NOTEBOOK_READING(identifier));
    }
    
    public void FORBIDDEN_NOTEBOOK_WRITING(String identifier) throws IOException{
       this.sendMessage(this._FORBIDDEN_NOTEBOOK_WRITING(identifier));
    }
    
     public void  FORBIDDEN_ANNOTATION_READING(String identifier) throws IOException {
        this.sendMessage(this._FORBIDDEN_ANNOTATION_READING(identifier));
    }
    
    public void  FORBIDDEN_ANNOTATION_WRITING(String identifier) throws IOException{
        this.sendMessage(this._FORBIDDEN_ANNOTATION_WRITING(identifier));
    }
    
    public void  FORBIDDEN_PERMISSION_CHANGING(String identifier) throws IOException{
        this.sendMessage(this._FORBIDDEN_PERMISSION_CHANGING(identifier));
    }

    public void  ILLEGAL_UUID(String identifier) throws IOException{
        this.sendMessage(this._ILLEGAL_UUID(identifier));
     }
    

    public void  REMOTE_PRINCIPAL_NOT_FOUND(String remoteID) throws IOException{
        this.sendMessage(this._REMOTE_PRINCIPAL_NOT_FOUND(remoteID));
    }
    
    
     public void  PRINCIPAL_NOT_FOUND(String externalIdentifier) throws IOException {
        this.sendMessage(this._PRINCIPAL_NOT_FOUND(externalIdentifier));
    }

    public void ANNOTATION_NOT_FOUND(String externalIdentifier) throws IOException{
        this.sendMessage(this._ANNOTATION_NOT_FOUND(externalIdentifier));
    }

    public  void   NOTEBOOK_NOT_FOUND(String externalIdentifier) throws IOException{
        this.sendMessage(this._NOTEBOOK_NOT_FOUND(externalIdentifier));
     }

    public  void   TARGET_NOT_FOUND(String externalIdentifier) throws IOException{
        this.sendMessage(this._TARGET_NOT_FOUND(externalIdentifier));
     }

    public  void   CACHED_REPRESENTATION_NOT_FOUND(String externalIdentifier) throws IOException{
        this.sendMessage(this._CACHED_REPRESENTATION_NOT_FOUND(externalIdentifier));
    }

    public  void   INVALID_PERMISSION_MODE(String permissionMode) throws IOException{
        this.sendMessage(this._INVALID_PERMISSION_MODE(permissionMode));
    }
    
    public  void  IDENTIFIER_MISMATCH(String externalIdentifier)throws IOException{
        this.sendMessage(this._IDENTIFIER_MISMATCH(externalIdentifier));
     }
  
    public  void  ADMIN_RIGHTS_EXPECTED()throws IOException{
        this.sendMessage(this._ADMIN_RIGHTS_EXPECTED());
     }
    
   public  void  DEVELOPER_RIGHTS_EXPECTED()throws IOException{
        this.sendMessage(this._DEVELOPER_RIGHTS_EXPECTED());
     }
   
     public void  PRINCIPAL_NOT_FOUND_BY_INFO(String email) throws IOException {
        this.sendMessage(this._PRINCIPAL_NOT_FOUND_BY_INFO(email));
    }
     
    public void   PRINCIPAL_IS_NOT_ADDED_TO_DB() throws IOException {
        this.sendMessage(this._PRINCIPAL_IS_NOT_ADDED_TO_DB());
    }
    
     public void   ACCOUNT_IS_NOT_UPDATED() throws IOException {
        this.sendMessage(this._ACCOUNT_IS_NOT_UPDATED());
    }
}
