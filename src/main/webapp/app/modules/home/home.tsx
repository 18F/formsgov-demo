import './home.scss';
import React, { useState, useEffect } from 'react';
import { Card, Button, CardTitle, CardText, Row, Col } from 'reactstrap';
import http from '../../shared/service/http-service';
import { Form, submission } from 'react-formio';
import uswds from '@formio/uswds';
import { Formio } from 'formiojs';
Formio.use(uswds);
export const Home = prop => {
  useEffect(() => {
    login();
  });

  const requestData = {
    data: {
      email: 'service@gsa.gov',
      password: 'vBEJbMK6DAydFjBitmLbB4ndBhHZpm'
    }
  };

  const handleSubmited = event => {
    console.log('handleSubmited ' + event);
  };
  const handleOnSubmit = event => {
    console.log('handleOnSubmit', event);
  };

  const login = async () => {
    http.post('https://dev-portal.fs.gsa.gov/dev/admin/login', requestData).then(response => {
      console.log(response.status);
      console.log(response.statusText);
      console.log(response.headers['x-jwt-token']);
    });
  };

  // const login = async () => {
  //   try {
  //     const { data: response }  = await (await http.post('https://dev-portal.fs.gsa.gov/dev/admin/login', requestData)).config.then(function(response) {
  //       console.log(response.data);
  //       console.log(response.status);
  //       console.log(response.statusText);
  //       console.log(response.headers);
  //       console.log(response.config);
  //     console.log(response.headers);
  //   } catch (error) {
  //     console.error('Error calling to https://dev-portal.fs.gsa.gov/dev/admin/login' + error);
  //   }
  // };
  return (
    <div>
      <Form src="https://dev-portal.fs.gsa.gov/dev/f8821form" onSubmitDone={handleSubmited} onSubmit={handleOnSubmit} />
    </div>
    // <div id="main-content" className="app-container" role="main">
    //   <Row>
    //     <Col sm="12">
    //       <h2>Form service</h2>
    //       <p>Selecet the form under Forms menu to fill out a form and submit.</p>
    //     </Col>
    //   </Row>
    // </div >
  );
};
export default Home;
