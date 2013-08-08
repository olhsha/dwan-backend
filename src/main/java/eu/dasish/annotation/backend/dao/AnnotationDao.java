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

import eu.dasish.annotation.backend.identifiers.AnnotationIdentifier;
import eu.dasish.annotation.backend.identifiers.SourceIdentifier;
import eu.dasish.annotation.schema.Annotation;
import eu.dasish.annotation.schema.AnnotationInfo;
import eu.dasish.annotation.schema.NewOrExistingSourceInfo;
import eu.dasish.annotation.schema.ResourceREF;
import java.sql.SQLException;
import java.util.List;

/**
 * Created on : Jun 27, 2013, 10:34:13 AM
 *
 * @author Peter Withers <peter.withers@mpi.nl>
 */

// TODO: Getting Target Sources from Body and testing must be added!!!


public interface AnnotationDao extends ResourceDao{
    
     /**
     * 
     * @param internalId
     * @return the external identifier for the annotation with internalId
     */
    public AnnotationIdentifier getExternalID(Number internalId);
    
    
    /**
     * 
     * @param annotationID
     * @return annotation which has an annotation ID "annotationID"
     * if externalID is null or such annotation does not exist in the DB returns null;
     */
    Annotation getAnnotation(Number annotationID) throws SQLException;
    
       
    /**
     * 
     * @param annotationId
     * @return the amount of deleted sources; removes _aid_ from the DB, together with its tagrget sources to
     * which no other annotations refers.
     */
    
    public int deleteAnnotation(Number annotationId) throws SQLException;
    
    /**
     * 
     * @param annotationIdentifier
     * @param sourceInfo
     * Only the table "annotations_target_sources" is affected
     * @return the amount of affected rows in the table "annotations_target_sources". Must be 1 if everything went well.
     */
    public int addAnnotationSourcePair(AnnotationIdentifier annotationIdentifier, SourceIdentifier sourceIdentifier);
    
   
    /**
     * 
     * @param annotation added to the table with annotations 
     * @return annotationIdentifier of the newly added annotation; returns null if something went wrong and annotation was not added or more than one row in the annotation table was affected
     */
    public Annotation addAnnotation(Annotation annotation, Number ownerID) throws SQLException;
 
   
    /**
     * 
     * @param annotation
     * @param sources
     * @param clear
     * @return annotation which target sources replaced (if clear == true) or extended (if clear -- false) by "sources" 
     * side-effects: updates the annotation_target_source table in the DB
     * adds a source if it is not in the DB
     * in the body (both, annotation-class, and in the table "annotation" replaced temporary sourceIDs of new sources
     * with persistent once once they are added to the DB
     */
    public Annotation updateSourceInfo(Annotation annotation, List<NewOrExistingSourceInfo> sources, boolean clear);
            
    
    
    public List<AnnotationInfo> getAnnotationInfos(List<Number> annotationIDs);    
     
   
    
    public List<ResourceREF> getAnnotationREFs(List<Number> annotationIDs);    
    
}
