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

import eu.dasish.annotation.backend.TestBackendConstants;
import eu.dasish.annotation.backend.TestInstances;
import eu.dasish.annotation.backend.dao.AnnotationDao;
import eu.dasish.annotation.backend.identifiers.AnnotationIdentifier;
import eu.dasish.annotation.schema.Annotation;
import eu.dasish.annotation.schema.ObjectFactory;
import javax.xml.bind.JAXBElement;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author olhsha
 */

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test-config/dataSource.xml", "/spring-test-config/mockAnnotationDao.xml", "/spring-test-config/mockNotebookDao.xml", "/spring-test-config/mockery.xml"})
public class AnnotationResourceTest {
    
    @Autowired
    private Mockery mockeryAnnotDao;
    @Autowired
    private AnnotationDao annotationDao;
    @Autowired
    private AnnotationResource annotationResource;
    
    public AnnotationResourceTest() {
    }
    
    
    /**
     * Test of getAnnotation method, of class AnnotationResource.
     */
    @Test
    public void testGetAnnotation() throws Exception {
        System.out.println("getAnnotation");
        final String annotationIdentifier= TestBackendConstants._TEST_ANNOT_1_EXT;
        final int annotationID = TestBackendConstants._TEST_ANNOT_1_INT;        
        final Annotation expectedAnnotation = (new TestInstances()).getAnnotationOne();
        // the result of the mocking chain is the same as the expected annotation.        
        mockeryAnnotDao.checking(new Expectations() {
            {
                oneOf(annotationDao).getAnnotationID(new AnnotationIdentifier(annotationIdentifier));                
                will(returnValue(annotationID));
                
                oneOf(annotationDao).getAnnotation(annotationID);                
                will(returnValue(expectedAnnotation));
            }
        });
         
        JAXBElement result = annotationResource.getAnnotation(annotationIdentifier);
        assertEquals(expectedAnnotation, result.getValue());
    }
}
