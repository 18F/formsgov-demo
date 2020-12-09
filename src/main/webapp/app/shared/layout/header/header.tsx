import './header.scss';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
// tslint:disable-next-line
const close = require('../../../../content/images/close.png');
// tslint:disable-next-line
const logo = require('../../../../content/images/faas-logo.svg');
const Header = props => {
  return (
    <div>
      <div className="usa-overlay" />
      <header className="usa-header usa-header--basic">
        <div className="usa-nav-container">
          <div className="usa-navbar">
            <div className="usa-logo" id="basic-logo">
              <Link to="/faas/ui">
                <img src={logo} role="img" alt="Form Service" />
              </Link>
            </div>
            <button className="usa-menu-btn ml-2">Menu</button>
          </div>
          <nav aria-label="Primary navigation" className="usa-nav">
            <button className="usa-nav__close">
              <img src={close} role="img" alt="close" />
            </button>
            <ul className="usa-nav__primary usa-accordion">
              <li className="usa-nav__primary-item">
                <button
                  className="usa-accordion__button usa-nav__link  usa-current"
                  aria-expanded="false"
                  aria-controls="basic-nav-section-one"
                >
                  <span>Forms</span>
                </button>
                <ul id="basic-nav-section-one" className="usa-nav__submenu">
                  <li className="usa-nav__submenu-item">
                    <Link to="/faas/ui/fheo">FHEO</Link>
                  </li>
                  <li className="usa-nav__submenu-item">
                    <a href="#" className="">
                      {' '}
                      MTW
                    </a>
                  </li>
                </ul>
              </li>
              <li className="usa-nav__primary-item">
                <button className="usa-accordion__button usa-nav__link" aria-expanded="false" aria-controls="basic-nav-section-two">
                  <span>Dashboard</span>
                </button>
                <ul id="basic-nav-section-two" className="usa-nav__submenu">
                  <li className="usa-nav__submenu-item">
                    <a href="#" className="">
                      {' '}
                      Report
                    </a>
                  </li>
                </ul>
              </li>
              <li className="usa-nav__primary-item">
                <a className="usa-nav__link" href="javascript:void(0)">
                  <span>Admin</span>
                </a>
              </li>
            </ul>
          </nav>
        </div>
      </header>
    </div>
  );
};

export default Header;
