<%--

    Copyright (C) 2013 DASISH

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.

--%>
<html>
    <body>
        <h2>DASISH REST API</h2>
        <p><a href="api/myresource">Jersey resource</a>
        <p><a href="https://trac.clarin.eu/wiki/DASISH/SpecificationDocument#RESTAPI">https://trac.clarin.eu/wiki/DASISH/SpecificationDocument#RESTAPI</a></p>
      
        <br> 
        <a href="api/authentication/login"> login  </a> <br> 
         <br> 
        <a href="api/authentication/logout"> logout</a> <br> 

        <b>Test URI-s</b><br>
        <b>All output xml-s are valid w.r.t. the schema</b><br>
        GET <a href="api/authentication/principal">api/authentication/principal</a> <br> 
        GET <a href="api/principals/admin">api/principals/admin</a><br> 
        GET <a href="api/principals/00000000-0000-0000-0000-0000000000112">api/principals/00000000-0000-0000-0000-0000000000112</a> <br> 
        GET <a href="api/principals/00000000-0000-0000-0000-0000000000112/current">api/principals/00000000-0000-0000-0000-0000000000112/current</a>  !Problem: how to ask the servlet if the given user is logged in, may be by some other running somewhere client<br> 
        GET <a href="api/principals/info?email=Twan.Goosen@mpi.nl">api/principals/info?email=Twan.Goosen@mpi.nl</a>  <br>
        GET <a href="api/annotations?link=Sagrada">api/annotations?link=Sagrada</a>  <br>
        GET <a href="api/annotations?link=Gaud">api/annotations?link=Gaud</a>  <br>
        GET <a href="api/annotations?after=2014-02-04 15:57:58.046908&before=2014-04-06 10:08:16.213186">api/annotations?after=2014-02-04 15:57:58.046908&before=2014-04-06 10:08:16.213186</a> 
        !Comment: What is "namespace" query parameter? Must be implemented and tested <br>
        GET <a href="api/annotations/00000000-0000-0000-0000-000000000021">api/annotations/00000000-0000-0000-0000-000000000021</a>  </br>
        GET <a href="api/annotations/00000000-0000-0000-0000-000000000021/targets">api/annotations/00000000-0000-0000-0000-000000000021/targets</a>  </br>
        GET <a href="api/annotations/00000000-0000-0000-0000-000000000021/permissions">api/annotations/00000000-0000-0000-0000-000000000021/permissions</a><br>
        GET <a href="api/targets/00000000-0000-0000-0000-000000000032">api/targets/00000000-0000-0000-0000-000000000032</a>  <br>
        GET <a href="api/targets/00000000-0000-0000-0000-000000000032/versions">api/targets/00000000-0000-0000-0000-000000000032/versions</a>   <br>
        GET <a href="api/cached/00000000-0000-0000-0000-000000000051/metadata">api/cached/00000000-0000-0000-0000-000000000051/metadata</a><br>
        GET <a href="api/cached/00000000-0000-0000-0000-000000000051/content">api/cached/00000000-0000-0000-0000-000000000051/content</a><br> 
        GET <a href="api/cached/00000000-0000-0000-0000-000000000511/stream">api/cached/00000000-0000-0000-0000-000000000511/stream</a> 
        
        <br> 
        <br>
        <b>Debugging URI's (only for developers)</b><br>
        GET <a href="api/debug/remoteID">api/debug/remoteID</a> <br>
        GET <a href="api/debug/annotations">api/debug/annotations</a> <br>
        updated: GET <a href="api/debug/logDatabase/32">api/debug/logDatabase/32</a> <br>
        updated: GET <a href="api/debug/logServer/32">api/debug/logServer/32</a> <br>
    </body>
</html>
