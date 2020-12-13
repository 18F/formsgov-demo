import './header.scss';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
// tslint:disable-next-line
const close = require('../../../../content/images/close.png');
// tslint:disable-next-line
const logo = require('../../../../content/images/faas-logo.svg');
// tslint:disable-next-line
const usFlag = require('../../../../content/images/us_flag_small.png');
// tslint:disable-next-line
const iconDot = require('../../../../content/images/icon-dot-gov.svg');
// tslint:disable-next-line
const iconHttps = require('../../../../content/images/icon-https.svg');
// tslint:disable-next-line
const irsLogo = require('../../../../content/images/irs-logo.png');
const Header = props => {
  return (
    <div>
      <a className="usa-skipnav" href="#main-content">Skip to main content</a>
      <div className="usa-banner">
        <div className="usa-accordion">
          <header className="usa-banner__header" style={{ backgroundColor: 'rgb(181 195 208 / 65%)' }}>
            <div className="usa-banner__inner">
              <div className="grid-col-auto">
                <img className="usa-banner__header-flag" src={usFlag} alt="U.S. flag" />
              </div>
              <div className="grid-col-fill tablet:grid-col-auto">
                <p className="usa-banner__header-text">An official website of the United States government</p>
                <p className="usa-banner__header-action" aria-hidden="true">Here’s how you know</p>
              </div>
              <button className="usa-accordion__button usa-banner__button" aria-expanded="false" aria-controls="gov-banner">
                <span className="usa-banner__button-text">Here’s how you know</span>
              </button>
            </div>
          </header>
          <div className="usa-banner__content usa-accordion__content" id="gov-banner" hidden>
            <div className="grid-row grid-gap-lg">
              <div className="usa-banner__guidance tablet:grid-col-6">
                <img className="usa-banner__icon usa-media-block__img" src={iconDot} alt="Dot gov" />
                <div className="usa-media-block__body">
                  <p>
                    <strong>
                      Official websites use .gov
                            </strong>
                    <br />
                        A <strong>.gov</strong> website belongs to an official government organization in the United States.

                        </p>
                </div>
              </div>
              <div className="usa-banner__guidance tablet:grid-col-6">
                <img className="usa-banner__icon usa-media-block__img" src={iconHttps} alt="Https" />
                <div className="usa-media-block__body">
                  <p>
                    <strong>
                      Secure .gov websites use HTTPS
                            </strong>
                    <br />
                        A <strong>lock</strong> (
                            <span className="icon-lock" />
                            ) or <strong>https://</strong> means you’ve safely connected to the .gov website. Share sensitive information only on official, secure websites.

                        </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="usa-overlay" />
      <header className="usa-header usa-header--basic mb-3">
        <div className="usa-nav-container">
          <div className="usa-navbar">
            <div className="usa-logo" id="basic-logo">
              <Link to="/">
                <img src={irsLogo} role="img" alt="Form Service" />
              </Link>
            </div>
            <button className="usa-menu-btn ml-2">Menu</button>
          </div>
          {/* <nav aria-label="Primary navigation" className="usa-nav">
            <button className="usa-nav__close">
              <img src={close} role="img" alt="close" />
            </button>
            <ul className="usa-nav__primary usa-accordion">
            <li className="usa-nav__primary-item">
                <a className="usa-nav__link usa-current">
                  <Link to="/faas/ui">Home</Link>
                </a>
              </li>
              <li className="usa-nav__primary-item">
                <button
                  className="usa-accordion__button usa-nav__link"
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
          </nav> */}
        </div>
      </header>
    </div>
  );
};

export default Header;
