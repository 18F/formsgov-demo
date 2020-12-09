const config = {
  VERSION: process.env.VERSION
};

export default config;

export const VERSION = process.env.VERSION;

export const AUTHORITIES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER'
};

export const messages = {
  DATA_ERROR_ALERT: 'Internal Error'
};

export const APP_DATE_FORMAT = 'DD/MM/YY HH:mm';
export const APP_TIMESTAMP_FORMAT = 'DD/MM/YY HH:mm:ss';
export const APP_LOCAL_DATE_FORMAT = 'DD/MM/YYYY';
export const APP_LOCAL_DATETIME_FORMAT = 'YYYY-MM-DDThh:mm';
export const APP_WHOLE_NUMBER_FORMAT = '0,0';
export const APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT = '0,0.[00]';

export const INVALID_LOOKUP_HEADER = 'Lookup Not Allowed';
export const INVALID_LOOKUP = "You may only select one value for a given element when using the 'Not =' operator.";
export const DUPLICATE_CRITERIA = 'Duplicate Criteria';
export const DUPLICATE_CRITERIA_MESSAGE = 'You cannot add element with same value to same Search Criteria Set.';
export const DUPLICATE_FINDER = 'Duplicate Finder';
export const DUPLICATE_FINDER_MESSAGE = 'You may only select one User Input File for each Search Criteria Set.';
export const FINDER_FILE_MESSAGE =
  'You may not include a User Input File with additional values for the same element.' +
  'First delete the selected values for the element and select the element for User Input File.';
export const INVALID_LOOKUP_SELECTION = 'invalid.lookup';
export const INVALID_CRITERIA_SELECTION = 'invalid.criteria';
export const INVALID_CRRITERIA_HEADER = 'Criteria Selection Not Allowed';
export const INVALID_CRRITERIA = 'mix.finder.value';
export const MAX_FIELD = 'Field Reached Maximum Limit';
export const MAX_FIELD_MESSAGE =
  'You can add up to 10 values for one Field and you can add maximum 20 Fields for each search criteria set.';
export const MOVE_TO_SET2 =
  'When adding an element with same value or finder file to same search criteria set, the selected criteria is moved to criteria set 2.';
export const HICAN_MESSAGE =
  'If you select HICAN finder file for search criterion and user input file as the operator, then you cannot use any other input files' +
  ' in the first or second search criteria set.Only one User Input File can be provided per HICAN request.';
export const MIX_OPERATOR_USERINPUT =
  'Criteria selection not allowed ' +
  'You may not include a User Input File with additional values for the same element.Hence, the selcted finder file is moved to criteria set 2.';
export const MIX_OPERATOR_USERINPUT_MESSAGE =
  'User input file with the same field is already applied for both criteria set and unable to add this criteria with the same field.';
export const MAX_FINDER_MESSAGE = 'Maximum of one user input file per criteria set is allowed. ';
export const DUP_NOT_ALLOWED = 'dup.criteria';
export const MAX_FINDER = 'max.finder';
export const ZIP_FINDER = 'Finder File Message';
export const ZIP_FINDER_MESSAGE =
  'A wild card "*" is needed in the 6th position for any 5-digit zip codes contained in the User Input File.';
export const finderFileNameErrMessage =
  'This request contains an old finder file name format.' +
  'Please follow the instructions in the criteria selection section to update the finder file name format and output , and then submit your request.';
