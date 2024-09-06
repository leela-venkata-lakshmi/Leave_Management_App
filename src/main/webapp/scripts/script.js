const urlParams = new URLSearchParams(window.location.search);
const error = urlParams.get('error');

if (error) {
    const errorMessageElement = document.getElementById('error-message');

    if (errorMessageElement) {
        if (error === 'invalidCredentials') {
            errorMessageElement.textContent = "Invalid Credentials";
        } else if (error === 'exceedsAllocatedLeave') {
            errorMessageElement.textContent = "You cannot request more than the allocated leave days.";
            errorMessageElement.style.display = 'block';
        }
    } else {
        console.error("Error message element not found.");
    }
}

// Fetch and display leave approvals
function fetchAndDisplayLeaves(statusFilter = '') {
    fetch('http://localhost:8080/Leave_Mgmt/approval')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('leave-requests-body');
            if (tbody) {
                tbody.innerHTML = ''; // Clear previous entries

                // Filter data based on statusFilter
                const filteredData = statusFilter ? data.filter(leave => leave.leaveStatus === statusFilter) : data;

                if (filteredData.length > 0) {
                    filteredData.forEach(leave => {
                        const row = document.createElement('tr');
                        let buttonsHtml = '';
                        if (leave.leaveStatus === 'PENDING') {
                            buttonsHtml = `
                                <form method="post" id="btnForm-${leave.reqId}" action="/Leave_Mgmt/leaveRequest">
                                    <input type="hidden" name="reqId" value="${leave.reqId}">
                                    <button type="submit" name="action" value="approve" class="btn btn-success btn-sm">Approve</button>
                                    <button type="submit" name="action" value="reject" class="btn btn-danger btn-sm">Reject</button>
                                </form>
                            `;
                        } else {
                            buttonsHtml = '<span class="badge bg-secondary p-2">Completed</span>';
                        }

                        row.innerHTML = `
                            <td>${leave.empId}</td>
                            <td>${leave.createdAt}</td>
                            <td>${leave.leaveType}</td>
                            <td>${leave.fromDate}</td>
                            <td>${leave.toDate}</td>
                            <td>${leave.reason}</td>
                            <td>
                                <span class="badge ${leave.leaveStatus === 'PENDING' ? 'bg-warning' :
                                    leave.leaveStatus === 'APPROVED' ? 'bg-success' :
                                    'bg-danger'}">
                                    ${leave.leaveStatus}
                                </span>
                            </td>
                            <td>
                                ${buttonsHtml}
                            </td>
                        `;
                        tbody.appendChild(row);
                    });
                } else {
                    tbody.innerHTML = '<tr><td colspan="8" class="text-center">No leave requests found.</td></tr>';
                }
            } else {
                console.error("Leave requests tbody element not found.");
            }
        })
        .catch(error => console.error('Error fetching leave requests:', error));
}


fetchAndDisplayLeaves(); // Fetch and display leave approvals on page load

// Event listeners for filters
console.log(document.getElementById('status-filter'));

function toggleDropdown() {

    // Fetch employee details when the modal is first shown
    const modalElement = document.getElementById('profileModal');
    $(modalElement).on('shown.bs.modal', function () {
        const employeeIdElement = document.getElementById('employeeId');
        const employeeNameElement = document.getElementById('employeeFirstName');
        const employeeLastNameElement = document.getElementById('employeeLastName');
        const employeeDob = document.getElementById('dob');
        const empGender = document.getElementById('empGender');
        const employeeContact = document.getElementById('employeeContact');

        // Fetch employee details if not already populated
        if (employeeIdElement.textContent.includes('Employee ID:')) {
            fetch('/Leave_Mgmt/employeeServlet')
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    employeeIdElement.textContent = `Employee ID: ${data.empId}`;
                    employeeNameElement.textContent = `Name: ${data.empFirstName}`;
                    employeeLastNameElement.textContent = ` ${data.empLastName}`;
                    employeeDob.textContent = `Date of Birth: ${data.dateOfBirth}`;
                    empGender.textContent= `Gender: ${data.gender}`;
                    employeeContact.textContent=`Contact No: ${data.phoneNumber}`;
                })
                .catch(error => console.error('Error fetching employee details:', error));
        }
    });
}












