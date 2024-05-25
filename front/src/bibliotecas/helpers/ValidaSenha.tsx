export class ValidacaoSenha
{
    
  public uppercase = false;
  public lowercase =  false;
  public number =  false;
  public specialChar =  false;
  public length =  false;
  public isOK = false;
    
}
export class ValidadorSenha
{
    public static valida(password: string):ValidacaoSenha {
        
        const uppercaseRegex = /[A-Z]/;
        const lowercaseRegex = /[a-z]/;
        const numberRegex = /[0-9]/;
        const specialCharRegex = /[^A-Za-z0-9]/;
    
        let isValid = new ValidacaoSenha();
        
        if (password == undefined) return isValid;

        isValid = {...isValid, 
            uppercase: uppercaseRegex.test(password),
            lowercase: lowercaseRegex.test(password),
            number: numberRegex.test(password),
            specialChar: specialCharRegex.test(password),
            length: password?.length >= 8, // Pode ser ajustado para o tamanho mínimo desejado
        };

        isValid.isOK =  isValid.length && isValid.lowercase && isValid.number && isValid.number && isValid.specialChar && isValid.uppercase;
        return isValid;
        

    }
}

