import React, { useState, useEffect } from 'react';
export const Admin = () => {
  useEffect(() => {
    document.title = 'Admin';
  }, []);
  return (
      <div>
        <p className="usa-text-large">Admin Page</p>
      </div>
    );
};
export default Admin;
