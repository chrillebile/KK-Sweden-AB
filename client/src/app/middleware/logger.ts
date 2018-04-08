import { Middleware } from 'redux';

export const logger: Middleware = (store) => (next) => (action : any) => {
  console.log(action);
  return next(action);
};
