
**Register user**
1. Verify register user status code and response with valid scenario
2. Veriy register user without email filed in reqest body and verify repose and status code
3. verif register user without password filed in reqest body and verify repose and status code
4. Verify register user with samme user , verify the reponse body and status code
5. Verify register user without user body, verify response body and status code
6. Verify register user with same details again and again , verify response and status code
7. Verify register user in db
8. Verfy api response when user tries to hit same details multiple times parallely

**Login user**
1.  Verify login user with valid creadentials, verify response token and staus code
2.  verify login user with invalid user , verify response code and error messgae
3.  verify logn user without email , verify response code and error message
4.  verify login user without password , verify reponse and error message
5.  verify login with request parameter body , veriyf reponse code and error message
6.  verify login api reponse hitting same credentials multiple time and parallely

**Create user**
1. Verify create user with valid credentials , verify response and status code
2. Verify create user with empty body , verify response and status code
3. verify create user without name , verify response and status code
4. verify create user wihout job , verify reponse and status code
5. verify data base entry for valid credentials

**List user**
1. Verify list user with valid page number, verify response and status code
2. verify list user with invalid page number , verify reponse and status code
3. Hit same page multiple times and parallely verify reponse and status code

**Delete user**
1. Verify Delete user with valid data, verify reponse and status code
2. Verify deleted user in db
3. Verify Delete user with id which is already deleted , verify reponse and status code
4. verify Delete user with invalid data , verify reponse and status code
