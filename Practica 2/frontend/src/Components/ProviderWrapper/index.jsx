import { I18nProvider } from "../../i18n";

const ProviderWrapper = (props) => {
  return(
    <I18nProvider locale={props.lang}>
      {props.children}
    </I18nProvider>
  )
  
}

export default ProviderWrapper