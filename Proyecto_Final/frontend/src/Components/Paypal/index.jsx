import React, {useEffect, useRef} from 'react'

export default function Paypal(props) {
  const paypal = useRef();
  
  useEffect(() =>{ 
    window.paypal.Buttons({
      createOrder: (data,actions,err) =>{
        return actions.order.create({
          intent: "CAPTURE",
          purchase_units: [
            {
              description: `Pago de evento con el plan: ${props.plan}`,
              amount: {
                value: props.price,
                currency_code: "USD"
              }
            }
          ]
        })
      },
      onApprove: async (data, actions) => {
        await (actions.order.capture())
        .then(props.onSuccess())
        
      },
      onError: (err)=> {
        console.error(err);
      }
    }).render(paypal.current)
  })
  return (
    <div>
      <div ref={paypal}></div>
    </div>
  )
}
