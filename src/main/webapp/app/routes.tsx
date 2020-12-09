import React from 'react';
import { Switch } from 'react-router-dom';
import Home from '../app/modules/home/home';
import ErrorBoundaryRoute from '../app/shared/error/error-boundary-route';
import PageNotFound from '../app/shared/error/page-not-found';
import Fheo from './modules/forms/fheo';
const Routes = () => (
  <div className="view-routes">
    <Switch>
      <ErrorBoundaryRoute path="/faas/ui/fheo" exact component={Fheo} />
      <ErrorBoundaryRoute path="/faas/ui" exact component={Home} />
      <ErrorBoundaryRoute component={PageNotFound} />
    </Switch>
  </div>
);

export default Routes;
