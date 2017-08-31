# Details About pcc Project
P. Code Challenge. Java based project to perform a solution for a defined REST API.

Structure into wks folder:
# Confiugration
./config: Maven and other configuration from third party components of the development environment
# Documentation
./doc: Project Documentation:
 - received: Documentation received. API specification, API description and example files 
 - solution: Documentation generated for the project and provided to final user
   - pcc-javadoc: Complete Javadoc of the project. RAR file is also provided
   - Arquitecture and Technical Solution.docx: Document describing the implemented solution.
# Implementation
# All projects are structured as standar maven projects
./pcc-def: Default parent maven project defining main dependencies and versions
./pcc-core: Business layer implementation. To make the example fully functional, an implementation of a Memory Map Data Base is also provided.
./pcc-web: REST controller and API with Springboot dependences
