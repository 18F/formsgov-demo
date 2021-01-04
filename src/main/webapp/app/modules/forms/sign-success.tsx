import React from 'react';
import { Link } from 'react-router-dom';
const SignSuccess = () => (
  <div>
    <div className="usa-alert usa-alert--success">
      <div className="usa-alert__body">
        <h3 className="usa-alert__heading">Document Signed Successfully</h3>
        <p className="usa-alert__text">
          You have succesfully signed the document,{' '}
          <a>
            <Link to="/">Click here to fill out the form again.</Link>
          </a>
        </p>
      </div>
    </div>
  </div>
);
export default SignSuccess;
