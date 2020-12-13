import React from 'react';
const SignUnsuccessful = () => (
  <div>
    <div className="usa-alert usa-alert--info">
      <div className="usa-alert__body">
        <h3 className="usa-alert__heading">Document Not Signed</h3>
        <p className="usa-alert__text">
          You have not signed the document, <a href="javascript:void(0);">Click here to fill out the form again.</a>
        </p>
      </div>
    </div>
  </div>
);
export default SignUnsuccessful;
