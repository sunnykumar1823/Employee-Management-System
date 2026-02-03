const apiUrl = "http://localhost:8080/employees";

// FORM SUBMIT (ADD / UPDATE)
document.getElementById("employeeForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const id = document.getElementById("empId").value;

    const employee = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        department: document.getElementById("department").value,
        salary: document.getElementById("salary").value
    };

    // UPDATE
    if (id !== "") {
        fetch(`${apiUrl}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(employee)
        })
        .then(res => res.json())
        .then(() => {
            alert("Employee Updated ✅");
            resetForm();
            loadEmployees();
        });
    }
    // ADD
    else {
        fetch(apiUrl, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(employee)
        })
        .then(res => res.json())
        .then(() => {
            alert("Employee Saved ✅");
            resetForm();
            loadEmployees();
        });
    }
});

// LOAD ALL
function loadEmployees() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(data => {
            const table = document.getElementById("employeeTable");
            table.innerHTML = "";

            data.forEach(emp => {
                table.innerHTML += `
                    <tr>
                        <td>${emp.id}</td>
                        <td>${emp.name}</td>
                        <td>${emp.email}</td>
                        <td>${emp.department}</td>
                        <td>${emp.salary}</td>
                        <td>
                            <button onclick="editEmployee(${emp.id}, '${emp.name}', '${emp.email}', '${emp.department}', ${emp.salary})">
                                Edit
                            </button>
                            <button onclick="deleteEmployee(${emp.id})">
                                Delete
                            </button>
                        </td>
                    </tr>
                `;
            });
        });
}

// DELETE
function deleteEmployee(id) {
    fetch(`${apiUrl}/${id}`, {
        method: "DELETE"
    })
    .then(() => {
        alert("Employee Deleted ❌");
        loadEmployees();
    });
}

// EDIT (fill form)
function editEmployee(id, name, email, department, salary) {
    document.getElementById("empId").value = id;
    document.getElementById("name").value = name;
    document.getElementById("email").value = email;
    document.getElementById("department").value = department;
    document.getElementById("salary").value = salary;

    document.querySelector("button[type='submit']").innerText = "Update Employee";
}

// RESET FORM
function resetForm() {
    document.getElementById("employeeForm").reset();
    document.getElementById("empId").value = "";
    document.querySelector("button[type='submit']").innerText = "Save Employee";
}

loadEmployees();
