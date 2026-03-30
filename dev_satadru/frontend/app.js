const apiBase = '/api';

const mainOptions = document.getElementById('main-options');
const registerSection = document.getElementById('registerSection');
const createGroupSection = document.getElementById('createGroupSection');
const proceedSection = document.getElementById('proceedSection');

const userList = document.getElementById('userList');
const groupList = document.getElementById('groupList');
const groupMembersSelect = document.getElementById('groupMembers');

const callUserSelect = document.getElementById('callUserSelect');
const callGroupSelect = document.getElementById('callGroupSelect');
const callStatus = document.getElementById('callStatus');
const remoteAudio = document.getElementById('remoteAudio');

document.getElementById('registerBtn').onclick = () => {
    mainOptions.classList.add('d-none');
    registerSection.classList.remove('d-none');
    refreshUsers();
};

document.getElementById('backFromRegister').onclick = () => {
    registerSection.classList.add('d-none');
    mainOptions.classList.remove('d-none');
};

document.getElementById('createGroupBtn').onclick = () => {
    registerSection.classList.add('d-none');
    createGroupSection.classList.remove('d-none');
    refreshUsersForGroup();
    refreshGroups();
};

document.getElementById('backFromGroup').onclick = () => {
    createGroupSection.classList.add('d-none');
    registerSection.classList.remove('d-none');
};

document.getElementById('proceedBtn').onclick = () => {
    mainOptions.classList.add('d-none');
    proceedSection.classList.remove('d-none');
    refreshUsersForCall();
    refreshGroupsForCall();
};

document.getElementById('backFromProceed').onclick = () => {
    proceedSection.classList.add('d-none');
    mainOptions.classList.remove('d-none');
};

// Add User form submission
document.getElementById('addUserForm').onsubmit = async (e) => {
    e.preventDefault();
    const name = document.getElementById('userName').value.trim();
    const phone = document.getElementById('userPhone').value.trim();
    if(!name || !phone) return alert("Name and phone are required");
    try {
        await axios.post(apiBase + '/users/add', {name: name, phoneNumber: phone});
        alert('User added successfully');
        document.getElementById('addUserForm').reset();
        refreshUsers();
    } catch(err) {
        alert(err.response.data || 'Error adding user');
    }
};

// Create Group form submission
document.getElementById('createGroupForm').onsubmit = async (e) => {
    e.preventDefault();
    const groupName = document.getElementById('groupName').value.trim();
    const selectedOptions = [...groupMembersSelect.selectedOptions];
    if(!groupName || selectedOptions.length === 0) return alert('Group name and members required');
    const memberIds = selectedOptions.map(opt => parseInt(opt.value));
    try {
        await axios.post(apiBase + '/groups/create', {groupName: groupName, memberIds: memberIds});
        alert('Group created successfully');
        document.getElementById('createGroupForm').reset();
        refreshGroups();
    } catch(err) {
        alert(err.response.data || 'Error creating group');
    }
};

// Fetch and display users
async function refreshUsers() {
    try {
        const res = await axios.get(apiBase + '/users/');
        const users = res.data;
        userList.innerHTML = '';
        users.forEach(user => {
            let li = document.createElement('li');
            li.textContent = `${user.name} (${user.phoneNumber})`;
            li.classList.add('list-group-item');
            userList.appendChild(li);
        });
    } catch(err) {
        alert('Error loading users');
    }
}

// Populate group member select dropdown
async function refreshUsersForGroup() {
    try {
        const res = await axios.get(apiBase + '/users/');
        const users = res.data;
        groupMembersSelect.innerHTML = '';
        users.forEach(user => {
            let option = document.createElement('option');
            option.value = user.id;
            option.textContent = `${user.name} (${user.phoneNumber})`;
            groupMembersSelect.appendChild(option);
        });
    } catch(err) {
        alert('Error loading users');
    }
}

// Fetch and display groups
async function refreshGroups() {
    try {
        const res = await axios.get(apiBase + '/groups/');
        const groups = res.data;
        groupList.innerHTML = '';
        groups.forEach(group => {
            let li = document.createElement('li');
            li.textContent = `${group.groupName} (${group.members.length} members)`;
            li.classList.add('list-group-item');
            groupList.appendChild(li);
        });
    } catch(err) {
        alert('Error loading groups');
    }
}

// For proceed section - populate call selects
async function refreshUsersForCall() {
    try {
        const res = await axios.get(apiBase + '/users/');
        const users = res.data;
        callUserSelect.innerHTML = '';
        users.forEach(user => {
            let option = document.createElement('option');
            option.value = user.id;
            option.textContent = `${user.name} (${user.phoneNumber})`;
            callUserSelect.appendChild(option);
        });
    } catch(err) {
        alert('Error loading users');
    }
}

async function refreshGroupsForCall() {
    try {
        const res = await axios.get(apiBase + '/groups/');
        const groups = res.data;
        callGroupSelect.innerHTML = '';
        groups.forEach(group => {
            let option = document.createElement('option');
            option.value = group.id;
            option.textContent = group.groupName;
            callGroupSelect.appendChild(option);
        });
    } catch(err) {
        alert('Error loading groups');
    }
}

// Placeholder voice call functionality (WebRTC setup to be added)
// Buttons to initiate calls
document.getElementById('callUserBtn').onclick = () => {
    alert('Voice call to single user feature to be implemented');
};

document.getElementById('callGroupBtn').onclick = () => {
    alert('Voice call to group feature to be implemented');
};

// WebRTC variables
let localStream = null;
let peerConnection = null;
const configuration = {
    iceServers: [
        { urls: 'stun:stun.l.google.com:19302' }, // Public Google STUN server
        {
            urls: 'turn:115.187.53.16:3478',
            username: 'turnuser',
            credential: 'turnpassword'
        }
    ]
};

let signalingSocket = null;
let currentUserId = null; // Current user's phone number or ID after registration

// Setup WebSocket signaling connection and register current user
async function setupSignaling(userId) {
    return new Promise((resolve, reject) => {
        signalingSocket = new WebSocket(`ws://${window.location.host}/ws/signaling`);

        signalingSocket.onopen = () => {
            signalingSocket.send(JSON.stringify({ type: "register", from: userId }));
            resolve();
        };

        signalingSocket.onerror = (e) => {
            console.error("WebSocket error", e);
            reject(e);
        };

        signalingSocket.onmessage = (msg) => {
            const data = JSON.parse(msg.data);
            if(data.type === "register" && data.status === "success") {
                console.log("Signaling registered successfully");
            } else {
                handleSignalingMessage(data);
            }
        };
    });
}

// Handle incoming signaling messages
function handleSignalingMessage(message) {
    switch(message.type) {
        case "offer":
            handleOffer(message);
            break;
        case "answer":
            handleAnswer(message);
            break;
        case "candidate":
            handleCandidate(message);
            break;
    }
}

// Initializes the peer connection with ICE handlers and media tracks
async function createPeerConnection(remoteUserId) {
    peerConnection = new RTCPeerConnection(configuration);

    peerConnection.onicecandidate = (event) => {
        if(event.candidate) {
            signalingSocket.send(JSON.stringify({
                type: "candidate",
                from: currentUserId,
                to: remoteUserId,
                data: event.candidate
            }));
        }
    };

    peerConnection.ontrack = (event) => {
        remoteAudio.srcObject = event.streams[0];
    };

    localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));
}

// Initiate call to a user
async function callUser(remoteUserId) {
    if(!currentUserId) {
        alert("You must proceed after registration.");
        return;
    }
    await createPeerConnection(remoteUserId);

    const offer = await peerConnection.createOffer();
    await peerConnection.setLocalDescription(offer);

    signalingSocket.send(JSON.stringify({
        type: "offer",
        from: currentUserId,
        to: remoteUserId,
        data: offer
    }));

    callStatus.textContent = `Calling ${remoteUserId}...`;
}

// Handle received offer (called by remote peer)
async function handleOffer(message) {
    await createPeerConnection(message.from);

    const desc = new RTCSessionDescription(message.data);
    await peerConnection.setRemoteDescription(desc);

    const answer = await peerConnection.createAnswer();
    await peerConnection.setLocalDescription(answer);

    signalingSocket.send(JSON.stringify({
        type: "answer",
        from: currentUserId,
        to: message.from,
        data: answer
    }));

    callStatus.textContent = `Call from ${message.from}`;
}

// Handle received answer (called by the caller peer)
async function handleAnswer(message) {
    const desc = new RTCSessionDescription(message.data);
    await peerConnection.setRemoteDescription(desc);
    callStatus.textContent = `Call established with ${message.from}`;
}

// Handle received ICE candidate
async function handleCandidate(message) {
    try {
        await peerConnection.addIceCandidate(new RTCIceCandidate(message.data));
    } catch(e) {
        console.error("Error adding received ice candidate", e);
    }
}

// Get user audio stream
async function getUserAudio() {
    try {
        localStream = await navigator.mediaDevices.getUserMedia({audio: true});
    } catch(e) {
        alert("Failed to get microphone access: " + e);
    }
}

// Modified proceed button handler to register user and setup signaling
document.getElementById('proceedBtn').onclick = async () => {
    // Prompt user to enter phone number to identify self before proceeding
    let phoneNumber = prompt("Enter your registered phone number:");
    if(!phoneNumber) {
        alert("Phone number is required to proceed.");
        return;
    }

    try {
        // Verify if this user exists
        const res = await axios.get(`${apiBase}/users/`);
        const users = res.data;
        const found = users.find(u => u.phoneNumber === phoneNumber);

        if(!found) {
            alert("User not registered. Please register first.");
            return;
        }

        currentUserId = phoneNumber;
        await setupSignaling(currentUserId);
        await getUserAudio();
        mainOptions.classList.add('d-none');
        proceedSection.classList.remove('d-none');
        refreshUsersForCall();
        refreshGroupsForCall();

        callStatus.textContent = "Ready to make calls.";

    } catch (error) {
        alert("Error verifying user: " + error);
    }
};

// Hook call buttons to actual calls
document.getElementById('callUserBtn').onclick = async () => {
    const remoteUserId = callUserSelect.options[callUserSelect.selectedIndex].text.match(/\((.+)\)/)[1]; // get phone number from text
    if(remoteUserId === currentUserId) {
        alert("Cannot call yourself.");
        return;
    }
    await callUser(remoteUserId);
};

document.getElementById('callGroupBtn').onclick = () => {
    alert("Group voice call feature will be implemented next.");
};
