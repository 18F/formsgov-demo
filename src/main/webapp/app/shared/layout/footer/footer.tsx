import './footer.scss';
import React from 'react';
// tslint:disable-next-line
const gsaLogo = require('../../../../content/images/gsa-footer-logo.png');
// tslint:disable-next-line
const faasLogo = require('../../../../content/images/faas-logo.png');
const Footer = () => (
  <footer className="usa-footer">
    <div className="usa-footer__secondary-section" style={{ backgroundColor: 'rgba(181, 195, 208, 0.65)' }}>
        <div className="grid-container">
        <div className="grid-row grid-gap">
          <div className="usa-footer__logo grid-row mobile-lg:grid-col-6 mobile-lg:grid-gap-2">
            <div className="mobile-lg:grid-col-auto">
              <p className="usa-text-small">Powered by</p>
            </div>
            <div className="mobile-lg:grid-col-auto">
              <img src={faasLogo} role="img" alt="" style={{ marginTop: '-15px' }} />
            </div>
          </div>
          {/* <div className="usa-footer__social-links grid-row grid-gap-1">
            <div className="grid-col-auto">
              <img src={faasLogo} role="img" alt="" />
              </div>
          </div> */}
          </div>
        </div>
      </div>
  </footer>
);
export default Footer;
