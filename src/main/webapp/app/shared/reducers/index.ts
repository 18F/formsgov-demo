import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';
import administration, { AdministrationState } from '../../../app/modules/administration/administration.reducer';
export interface IRootState {
  readonly administration: AdministrationState;
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  administration,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
