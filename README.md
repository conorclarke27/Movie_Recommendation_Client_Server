# Movie Recommender Client-Server
*Authors: Conor Clarke, Illya Chaban*

## Object Oriented Programming Project  

### Objectives:  

To practice the following:  
- Design and implementation of an application in Java to demonstrate the use of the Collections framework
- To use regular expressions to validate input•To design and develop a multithreaded application
- To design and develop a database-driven client-server application
- To design and implement a protocol that will allow communication between distributed computers
- To gain experience testing the functionality of the application

### Functional Requirements:

You are required to develop an information server for storing movie information. Your server will use a database as the back end storage and will allow users to query the database from a range of clients. Your server will need to provide the following functionality:  
- Register: Sends the client a persistent id which they can use to interact with the server. This id will be used to store movies thata user has watched and will aid the recommend function.
- Search: Allows clients to search for a movie based on either title or director.
- Add: Allows clients to add new movies to the database.
- Remove: Allows clients to remove data from the database.
- Update: Allows users to update information about a movie.
- Watch: Records what movies have been watched by the client.
- Recommend: Will recommend movies to a user based on what they have watched.

You will need to design and implement a protocol that will allow the clients to communicate with the server. This may be done as a series of text statements. For example, if a user wants to search for movies the client could send the following string:  
  
  **Search &nbsp; &nbsp; &nbsp; 1 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; My Left Foot**  
  **Command &nbsp;UserID Detail**  
    
The server should be able to process this statement and return a list of movies relating to the search term provided. It should store the queries from the user. You should ensure that the server can handle any data sent to it by the client e.g. multi-word search terms.

All data must be returned to the client in JSON format. For this, you need to create a JSON object to be returned to the client with all of the relevant results.

When a search is performed, resultsshould be cached in an appropriate data structure so that if the same search is repeated, possibly by another user, the system does not need to call the database again.

The server should spawn a thread per client.







