# Details About pcc Project
P. Code Challenge. Java based project to perform a solution for a defined REST API.
Please, see structure into wks folder for more details

# Confiugration
wks/config: Maven and other configuration from third party components of the development environment

# Documentation
wks/doc: Project Documentation:
 - received: Documentation received. API specification, API description and example files 
 - solution: Documentation generated for the project and provided to final user
   - pcc-javadoc: Complete Javadoc of the project. RAR file is also provided
   - Royalty Manager Architecture and Technical Solution.pdf: Document describing the implemented solution.
# Implementation
All projects are structured as standard maven projects
- wks/pcc-def: Default parent maven project defining main dependencies and versions
- wks/pcc-core: Business layer implementation.
- wks/pcc-dao-map: Data Access layer implementation. To make the example fully functional, an implementation of a Memory Map Data Base is provided.
- wks/pcc-web: REST controller and API with Springboot dependences
