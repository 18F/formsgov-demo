import './home.scss';
import React from 'react';
import { connect } from 'react-redux';
export type IHomeProp = StateProps;
export const Home = (props: IHomeProp) => {
  const { account } = props;

  return (
    <h2>Welcome, Formservice!</h2>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
