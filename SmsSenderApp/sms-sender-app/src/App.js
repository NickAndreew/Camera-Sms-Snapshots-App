import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {

	startSending = event => {
        
        var Http = new XMLHttpRequest();
		var url = "http://localhost:8080/getMMS"
		var data = new FormData();
	
		var location = document.getElementById('location').value;
		var phoneNumber = document.getElementById('phoneNumber').value;
	 
		data.append('phoneNumber', location);
		data.append('location', phoneNumber);
	
		Http.open("POST", url);
		Http.setRequestHeader("Access-Control-Allow-Origin", "http://localhost:8080/");
		Http.send(data);
		alert("Sms has been sent");
        
	};

	render() {
		return (
			<div className="App">
				<form id="sms-send-form" method="POST" enctype="multipart/form-data" action="/">
                    <label name="phoneNumber">Your phone number</label>
                    <input type="text" name="phoneNumber" id="phoneNumber" required/>
                    
                    <label name="">Location</label>
                    <select name="location" id="location" required>
                        <option value="Kyiv">Kyiv</option>
                        <option value="Kharkiv">Kharkiv</option>
                        <option value="Poltava">Poltava</option>
                        <option value="Dnipropetrovsk">Dnipropetrovsk</option>
                    </select>
					<button type="submit" onClick={this.startSending}>Send SMS</button>
                </form>
			</div>
		);
	}
}

export default App;
