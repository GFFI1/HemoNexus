# **BloodDonorHub**

## **Overview**
BloodDonorHub is a platform designed to facilitate efficient blood donation and management. It connects users, blood banks, and their managers to ensure the availability of blood for those in need. The platform supports functionalities such as user registration, blood donation, appointment scheduling, and inter-blood bank requests.

---

## **Features**

### **User Features:**
1. **Registration and Login**:
   - Users can register as either an **Admin** or a **Normal User**.
   - Users can securely log in to the platform.

2. **Blood Donation**:
   - Users can donate blood to campaigns created by blood bank managers.
   - Donations are tracked and associated with campaigns or specific blood banks.

3. **Blood Requests**:
   - Users can raise a ticket to request blood from a blood bank.
   - Tickets remain open until the user receives the requested blood.

4. **Appointment Scheduling**:
   - Users can schedule appointments for requesting specific blood types.
   - Once approved by a blood bank manager, a ticket is raised for the appointment.

5. **Filtering Blood Banks**:
   - Users can search and filter blood banks by city and blood type availability.

---

### **Manager Features**:
1. **Campaign Management**:
   - Managers can create campaigns for blood donation drives.
   - Campaigns can be opened or closed as needed.

2. **Blood Bank Management**:
   - Managers oversee the inventory of available blood types and their quantities.
   - Managers can view and manage all transactions related to their blood bank.

3. **Ticket Handling**:
   - Managers can view and process blood request tickets raised by users.
   - Tickets can be accepted, rejected, or transferred to other blood banks if blood is unavailable.

4. **Transaction Filtering**:
   - Managers can filter transactions by username to track blood usage.

5. **Inter-Blood Bank Requests**:
   - Blood banks can request blood from other banks in case of shortage.

---

## **System Workflow**

### **For Users**:
1. Register as Admin or User and log in to the platform.
2. Search blood banks by city and blood type.
3. Raise blood request tickets or schedule appointments.
4. Donate blood directly to campaigns created by blood bank managers.

### **For Managers**:
1. Manage blood bank inventory and view all transactions.
2. Process user tickets by approving, rejecting, or transferring requests.
3. Create and manage campaigns for blood donation.
4. Facilitate inter-blood bank requests to maintain stock levels.

---

## **System Architecture**

The system consists of the following components:

1. **Frontend**: 
   - Provides a user-friendly interface for users and managers to interact with the platform.
2. **Backend**:
   - Handles all the business logic, including user authentication, ticket processing, and inventory management.
3. **Database**:
   - Stores user data, blood bank inventory, campaigns, tickets, and transactions.

---

## **Architecture Diagram**

1. **Users** interact with the **Frontend** to perform activities like registration, ticket raising, and filtering blood banks.
2. The **Backend Server** processes these requests and communicates with the database for storing or retrieving relevant data.
3. **Managers** use the platform for inventory, campaign, and ticket management.
4. **Inter-Blood Bank Requests** are facilitated through backend logic to ensure smooth communication between blood banks.

---

## **Tech Stack**
- **Frontend**: React.js
- **Backend**: Spring Boot
- **Database**: MySQL
- **Docker**: To containerize the backend and database services for seamless deployment.

---

## **Future Enhancements**
1. Implement notification systems for users when their tickets are processed.
2. Add analytics dashboards for managers to track donation trends and blood usage.
3. Introduce AI-based recommendations for managing inventory efficiently.
