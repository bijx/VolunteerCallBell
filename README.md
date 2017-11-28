# Volunteer Call Bell: Demo System

The Volunteer Call Bell System is an integrated Android application that allows patients at hospitals to call volunteers independent of the already integrated Nurse Call Bell System. This allows less urgent requests (i.e. turning on the television, grabbing a glass of water, picking up a toothbrush) to be fulfilled by a non-medically trained volunteer. This leaves serious or life-threatening calls to be responded to by trained nurses.

## Android App
The system uses an Android application that allows patients to submit a request for a volunteer to a cloud database (stored on Google Firebase) which contains the following information:
- Room Number
- Timestamp
- Call Status (active or completed)

Once the request has been submitted, volunteer tablet devices can receive all active calls to a list on a receiving device. Once a call has been marked as completed, it is removed from the list. **Note: Patients can terminate the request at any time should they feel no longer in need of assistance.**

## Database Storage & Management
Upon receiving a call request, the database creates a new entry for each call with a unique ID. The call is added to two database sub-archives: ```activeCalls``` and ```allCalls```. The receiver app grabs its data from activeCalls and once a call is complete, the data is permanently deleted from the section. The second section is used for data management and statistics. Hospital staff can check basic analytics such as volume of calls from specific rooms and peak call times to better organize their resources in the future.

### Authentication & Privacy
Currently, there is no authentication needed to read and write to the database, however the systems are in place to add any style of authentication (email/password, anonymous, two-factor, etc.).


## ToDo

- [x] Create Receiver App
- [x] Implement Firebase connectivity
- [ ] Authentication
- [ ] Patient name addition (to call request)



*The app and call bell system was designed by students at the University of Ottawa.*