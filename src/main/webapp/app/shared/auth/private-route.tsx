import React from 'react';
import { connect } from 'react-redux';
import { Route, RouteProps } from 'react-router-dom';
import { IRootState } from '../../../app/shared/reducers';
import ErrorBoundary from '../../../app/shared/error/error-boundary';

interface IOwnProps extends RouteProps {
  hasAnyAuthorities?: string[];
}

export interface IPrivateRouteProps extends IOwnProps, StateProps {}

export const PrivateRouteComponent = ({ component: Component, hasAnyAuthorities = [], ...rest }: IPrivateRouteProps) => {
  const checkAuthorities = props => (
    <ErrorBoundary>
      <Component {...props} />
    </ErrorBoundary>
  );
  if (!Component) throw new Error(`A component needs to be specified for private route for path ${(rest as any).path}`);
  return <Route {...rest} />;
};

export const hasAnyAuthority = (authorities: string[], hasAnyAuthorities: string[]) => {
  if (authorities && authorities.length !== 0) {
    if (hasAnyAuthorities.length === 0) {
      return true;
    }
    return hasAnyAuthorities.some(auth => authorities.includes(auth));
  }
  return false;
};

const mapStateToProps = ({ hasAnyAuthorities = [] }: IOwnProps) => ({});

type StateProps = ReturnType<typeof mapStateToProps>;

/**
 * A route wrapped in an authentication check so that routing happens only when you are authenticated.
 * Accepts same props as React router Route.
 * The route also checks for authorization if hasAnyAuthorities is specified.
 */
export const PrivateRoute = connect<StateProps, undefined, IOwnProps>(
  mapStateToProps,
  null,
  null,
  { pure: false }
)(PrivateRouteComponent);

export default PrivateRoute;
