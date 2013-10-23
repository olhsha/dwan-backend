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
package eu.dasish.annotation.backend.dao;

import java.util.UUID;

/**
 *
 * @author olhsha
 */
public interface ResourceDao {
    
    
    public void setServiceURI(String serviceURI);
    
     /**
     * 
     * @param externalID
     * @return internal identifier of the reTarget with externalID, or null if there is no reTarget with this identifier
     */
    public Number getInternalID(UUID externalId);
    
    
     /**
     * 
     * @param uri
     * @return internal identifier of the reTarget with uri, or null if there is no reTarget with this uri
     */
    public Number getInternalIDFromURI(String uri);
    
    /**
     * 
     * @param internalId
     * @return the UUID (external ID) of the reTarget with the "internalID".
     */
    public UUID getExternalID(Number internalId);
    
    /**
     * 
     * @param externalID
     * @return returns the URI which is a concatenation of the serviceURI and externalID
     */
    public String externalIDtoURI(String externalID);
    
    /**
     * 
     * @param stringURI
     * @return returns the extranlID which is a suffix of stringURI
     */
    public String stringURItoExternalID(String stringURI);
    
    /**
     * 
     * @param internalID
     * @return URI string of the resource )of the type set in resourceTableName) with internalID
     */
    public String getURIFromInternalID(Number internalID);
}
