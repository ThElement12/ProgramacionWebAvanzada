import {Fragment} from 'react';
import { IntlProvider} from 'react-intl';
import { LOCALES } from './constants';
import messages from './messages';

const Provider = ({children, locale = LOCALES.SPANISH}) =>(
  <IntlProvider textComponent={Fragment}
    locale={locale}
    messages={messages[locale]}>
      {children}
    </IntlProvider>
)

export default Provider;