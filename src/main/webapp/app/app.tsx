import 'react-toastify/dist/ReactToastify.css';
import './app.scss';
import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Card } from 'reactstrap';
import { BrowserRouter as Router } from 'react-router-dom';
import { ToastContainer, toast, ToastPosition } from 'react-toastify';
import { IRootState } from '../app/shared/reducers';
import Header from '../app/shared/layout/header/header';
import Footer from '../app/shared/layout/footer/footer';
import { hasAnyAuthority } from '../app/shared/auth/private-route';
import ErrorBoundary from '../app/shared/error/error-boundary';
import { AUTHORITIES } from '../app/config/constants';
import AppRoutes from '../app/routes';

const baseHref = document
  .querySelector('base')
  .getAttribute('href')
  .replace(/\/$/, '');

export const App = props => {
  const paddingTop = '0px';
  return (
    <Router basename={baseHref}>
      <div style={{ paddingTop }}>
        <ToastContainer
          position={toast.POSITION.TOP_LEFT as ToastPosition}
          className="toastify-container"
          toastClassName="toastify-toast"
        />
        <div className="container-fluid view-container" id="app-view-container">
          <Header />
          <Card>
            <ErrorBoundary>
              <AppRoutes />
            </ErrorBoundary>
          </Card>
          <Footer />
        </div>
      </div>
    </Router>
  );
};
export default App;
