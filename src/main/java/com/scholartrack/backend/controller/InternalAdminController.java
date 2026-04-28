package com.scholartrack.backend.controller;

import com.scholartrack.backend.model.User;
import com.scholartrack.backend.repository.UserRepository;
import com.scholartrack.backend.repository.ScholarshipRepository;
import com.scholartrack.backend.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal-setup")

public class InternalAdminController {

    @Autowired private UserRepository userRepository;
    @Autowired private ScholarshipRepository scholarshipRepository;
    @Autowired private ApplicationRepository applicationRepository;

    @GetMapping
    public String getSetupPage() {
        return "<html><head><title>Admin DB Management</title><style>" +
               "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f0f2f5; margin: 0; padding: 20px; color: #333; }" +
               ".container { max-width: 1200px; margin: 0 auto; }" +
               ".card { background: white; border-radius: 10px; padding: 25px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); margin-bottom: 30px; }" +
               ".header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; border-bottom: 2px solid #eee; padding-bottom: 15px; }" +
               "h1 { color: #1a73e8; margin: 0; }" +
               "h2 { color: #3c4043; border-left: 4px solid #1a73e8; padding-left: 10px; margin-bottom: 20px; }" +
               "table { width: 100%; border-collapse: collapse; margin-top: 10px; font-size: 14px; }" +
               "th { background: #f8f9fa; text-align: left; padding: 12px; border-bottom: 2px solid #dee2e6; color: #5f6368; }" +
               "td { padding: 12px; border-bottom: 1px solid #eee; }" +
               "tr:hover { background: #f1f3f4; }" +
               ".badge { padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; text-transform: uppercase; }" +
               ".badge-admin { background: #e8f0fe; color: #1967d2; }" +
               ".badge-student { background: #fce8e6; color: #c5221f; }" +
               ".btn-delete { color: #d93025; background: none; border: 1px solid #d93025; padding: 4px 8px; border-radius: 4px; cursor: pointer; font-size: 12px; }" +
               ".btn-delete:hover { background: #d93025; color: white; }" +
               ".form-group { display: flex; gap: 10px; margin-bottom: 20px; align-items: flex-end; }" +
               ".form-field { display: flex; flex-direction: column; flex: 1; }" +
               "input { padding: 8px 12px; border: 1px solid #dadce0; border-radius: 4px; margin-top: 4px; }" +
               ".btn-primary { background: #1a73e8; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; font-weight: 600; }" +
               ".btn-primary:hover { background: #174ea6; }" +
               "</style></head><body>" +
               "<div class='container'>" +
               "  <div class='header'><h1>ScholarTrack DB Management</h1><p>Internal Backend Access Tools</p></div>" +
               
               "  <div class='card'>" +
               "    <h2>System Users</h2>" +
               "    <div class='form-group'>" +
               "      <div class='form-field'><label>Name</label><input type='text' id='userName' placeholder='John Doe'></div>" +
               "      <div class='form-field'><label>Email</label><input type='email' id='userEmail' placeholder='john@example.com'></div>" +
               "      <div class='form-field'><label>Password</label><input type='password' id='userPass' placeholder='Password'></div>" +
               "      <button class='btn-primary' onclick='addUser(\"admin\")'>Create Admin</button>" +
               "    </div>" +
               "    <table id='usersTable'><thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th><th>Actions</th></tr></thead><tbody id='usersBody'></tbody></table>" +
               "  </div>" +

               "  <div class='card'>" +
               "    <h2>Scholarships List</h2>" +
               "    <table id='scholarTable'><thead><tr><th>ID</th><th>Title</th><th>Amount</th><th>Category</th><th>Deadline</th></tr></thead><tbody id='scholarBody'></tbody></table>" +
               "  </div>" +

               "  <div class='card'>" +
               "    <h2>Active Applications</h2>" +
               "    <table id='appsTable'><thead><tr><th>ID</th><th>Student</th><th>Scholarship</th><th>Status</th><th>GPA</th></tr></thead><tbody id='appsBody'></tbody></table>" +
               "  </div>" +
               "</div>" +

               "<script>" +
               "function fetchAll() {" +
               "  fetch('/internal-setup/data').then(res => res.json()).then(data => {" +
               "    renderUsers(data.users || []);" +
               "    renderScholarships(data.scholarships || []);" +
               "    renderApplications(data.applications || []);" +
               "  }).catch(err => console.error('Fetch error:', err));" +
               "}" +
               "function renderUsers(users) {" +
               "  document.getElementById('usersBody').innerHTML = users.map(u => `<tr>" +
               "    <td>${u.id}</td>" +
               "    <td><b>${u.name}</b></td>" +
               "    <td>${u.email}</td>" +
               "    <td><span class='badge badge-${u.role}'>${u.role}</span></td>" +
               "    <td><button class='btn-delete' onclick='deleteUser(${u.id})'>Wipe Account</button></td>" +
               "  </tr>`).join('');" +
               "}" +
               "function renderScholarships(s) {" +
               "  document.getElementById('scholarBody').innerHTML = s.map(x => `<tr>" +
               "    <td>${x.id}</td>" +
               "    <td>${x.title}</td>" +
               "    <td><b>$${x.amount}</b></td>" +
               "    <td>${x.category}</td>" +
               "    <td>${x.deadline}</td>" +
               "  </tr>`).join('');" +
               "}" +
               "function renderApplications(a) {" +
               "  document.getElementById('appsBody').innerHTML = (a || []).map(x => `<tr>" +
               "    <td>${x.id}</td>" +
               "    <td>${x.studentName}</td>" +
               "    <td>${x.scholarship}</td>" +
               "    <td>${x.status}</td>" +
               "    <td>${x.cgpa || 'N/A'}</td>" +
               "  </tr>`).join('');" +
               "}" +
               "function addUser(role) {" +
               "  const name = document.getElementById('userName').value; " +
               "  const email = document.getElementById('userEmail').value; " +
               "  const password = document.getElementById('userPass').value;" +
               "  if(!name || !email || !password) { alert('Fill all fields'); return; }" +
               "  fetch('/internal-setup/create', { " +
               "    method: 'POST', " +
               "    headers: { 'Content-Type': 'application/json' }, " +
               "    body: JSON.stringify({ name, email, password, role }) " +
               "  }).then(() => {" +
               "    document.getElementById('userName').value='';" +
               "    document.getElementById('userEmail').value='';" +
               "    document.getElementById('userPass').value='';" +
               "    fetchAll();" +
               "  });" +
               "}" +
               "function deleteUser(id) {" +
               "  if(confirm('Delete user memory permanently?')) fetch('/internal-setup/user/'+id, { method: 'DELETE' }).then(() => fetchAll());" +
               "}" +
               "fetchAll();" +
               "</script></body></html>";
    }

    @GetMapping("/data")
    public java.util.Map<String, Object> getAllData() {
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("users", userRepository.findAll());
        response.put("scholarships", scholarshipRepository.findAll());
        response.put("applications", applicationRepository.findAll());
        return response;
    }

    @PostMapping("/create")
    public void createAdmin(@RequestBody User user) {
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) { throw new RuntimeException(e); }
    }
}
