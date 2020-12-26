import './footer.scss';
import React from 'react';
// tslint:disable-next-line
const gsaLogo = require('../../../../content/images/gsa-footer-logo.png');
// tslint:disable-next-line
const faasLogo = require('../../../../content/images/faas-logo-white.png');
const Footer = () => (
  <footer className="usa-footer">
    <div className="usa-footer__secondary-section" style={{ backgroundColor: '#1b1b1b', color: '#fff' }}>
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
          <div className="usa-footer__contact-links mobile-lg:grid-col-6">
            <address className="usa-footer__address">
              <div className="usa-footer__contact-info grid-row grid-gap">
                <div className="grid-col-auto">Release 1.0</div>
              </div>
            </address>
          </div>
        </div>
      </div>
    </div>
  </footer>
);
export default Footer;
