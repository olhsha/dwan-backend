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

import eu.dasish.annotation.schema.User;
import eu.dasish.annotation.schema.UserInfo;


/**
 *
 * @author olhsha
 */
public interface UserDao extends ResourceDao{
    
     public User getUser(Number internalID);
     
     public User getUserByInfo(String  eMail);
     
     UserInfo getUserInfo(Number internalID);
     
     public Number addUser(User user, String remoteID);
     
     public int deleteUser(Number intenralID);
  
     public boolean userIsInUse(Number userID);
     
     public boolean userExists(User user);
}


