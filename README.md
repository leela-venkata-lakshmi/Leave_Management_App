# Leave_Management_App

# OVERVIEW:

This application is developed for employees to submit their leave requests and the managers of employees will approve or reject the employee leave applications.

Added functionalities:

Login: 
<img width="935" alt="leave_mgmt1" src="https://github.com/user-attachments/assets/f82d0adf-4c61-465c-9f55-905133ab49a5">

Login page created successfully by allowing only valid users into the dashboard by fecthing login credentials from the database.Used Bcrypt hashing for passwords.

Dashboard:
LeaveSummary & Upcoming Holidays:
<img width="924" alt="leave_mgmt2" src="https://github.com/user-attachments/assets/559af3f6-9dc0-41b9-a86b-c4a3d4a19bca">

Included Leave Summary, Upcoming Holidays , Links to leave application, myleaves, leave approvals and profile icon in the dashboard.

Profile icon & Logout: 
onclick will fetch the employee details and will display. 
And profile icon has a logout option. Onclick,   invalidates user session and which redirects to login page.
<img width="916" alt="leave_mgmt3" src="https://github.com/user-attachments/assets/a1cdf8ad-e3aa-4f3d-a3b1-2290e81ac56d">


Leave Application:
Validations added.
Validations like which provides gender specific options for leave reason, shows acknowledge messages for weekends and will not let the user to select the past dates.<img width="802" alt="leave_mgmt4" src="https://github.com/user-attachments/assets/aa9b6087-366c-45ef-9081-c4348267d55a">


My Leaves:
Shows the history my employee leaves along with all details of leave and it's status.
Employee can filter and view the leaves by the status pending, approved, rejected in the his/her leave history.
<img width="866" alt="leave_mgmt7" src="https://github.com/user-attachments/assets/a032540d-db08-403e-ba9c-b1fdab006d13">


Leave Approvals:
If the employee is also a manager , then he can view all his employee leave requests in his leave approvals. Manager can perform actions like accept or reject on leave request. And the leave request status also updated after performing action from the backend.
<img width="892" alt="leave_mgmt5" src="https://github.com/user-attachments/assets/8557c5ba-143a-4fcb-96b8-f10866e223fe">


Manager can also filter leave requests based on status.
<img width="858" alt="leave_mgmt6" src="https://github.com/user-attachments/assets/8fa62539-b371-4b43-b8f9-51e10c80196b">

Authontication Filter:
Authontication added in a way that no user can access the application without login using webFilter. If user try to access any pages without login then it will redirect to the login page.

Thank You.

