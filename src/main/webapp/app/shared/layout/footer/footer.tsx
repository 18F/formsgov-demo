import './footer.scss';
import React from 'react';
// tslint:disable-next-line
const gsaLogo = require('../../../../content/images/gsa-footer-logo.png');
// tslint:disable-next-line
const faasLogo = require('../../../../content/images/faas-logo.png');
const Footer = props => (
  <div>
    <footer className="usa-footer usa-footer--slim mt-3">
      {/* <div className="grid-container usa-footer__return-to-top">
        <a href="#">Return to top</a>
      </div> */}
      <div className="usa-footer__secondary-section" style={{ background: 'rgb(181 195 208 / 65%)' }}>
        <div className="grid-container">
          <div className="usa-footer__logo grid-row grid-gap-2">
            <div className="grid-col-auto">
              <h3 className="usa-footer__logo-heading">Powered By</h3>
            </div>
            <div className="grid-col-auto" style={{ marginRight: '18rem !important' }}>
              <img src={faasLogo} role="img" alt="" />
            </div>
            {/* <div className="grid-col-auto">
              <h3 className="release-lable">Release 1.0</h3>
            </div> */}
          </div>
        </div>
      </div>
    </footer>
  </div>
);
export default Footer;
