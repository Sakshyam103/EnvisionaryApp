import React from 'react';
import NavigationBar from "./NavigationBar.jsx";

const AboutUs = () => {
    const linkedInLogo = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAABZElEQVR4nO2ZvUoDQRSFP1Hs/AFtBQt9A99BwV20FIV0lr6CjQTyID6DnSbqg9gp/iDopkpzZWEaw+zOzFrsHbkHThNuZs+Xu3dmk4DJZOqqI2ACTAHp2VNgDJSpECMF4aXBw5ROiHIXMSATBUEl4LsYkEpBUAn4OwZEMnFQfQcUA5lT2yfyApwAG85nwFuOHdlXtF0H1fbmVU/9Wo4gpae+yBHkHRgAm871jLzmCCKKHFTfAcVA5tSlnSk1j+4s2gKWgRVgD7gEPnIAqQ/Og8C114Eb7SA7MRcHloAHzSAp2gZmOYAsRtRcawa5AJ5d3RNw3lJ7qhXkOPH7965WkPuGNevfCOjwMNobyGfDmvXrPi1oBfnrumIgWEe8slsLmxFs1xL/eNiMiJ0jtG+TmhzUvwGpFISUgL9iQMYKgkrAtzEgpYKgEvAhkRoqCCsNviJRhfv3VMPMVO52iu6EyWTil34AFdRDhxtrT8cAAAAASUVORK5CYII=";
    const githubLogo = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAADtklEQVR4nO2ZSWgVQRCGP2OCS3CJYoy7uCtiDi6o8aAIikvQi4oGvCiiRo2E6FXJQdxQg4LgUTx4cyPuHhVRD0bcsyDu4IJrTNTnSEMNPOfNm1czb2YSJD8UDNNT1fV3V1dX90AH/l8UAEuBfUAt8Bj4CLSKmOdH0ma+WQL0pp2gC1AGXAJ+A5ZPMToXgFViK3Z0AyqBVwGcTycvga1A17hILAAaQiTglHpgfpQEzNTXREjAKcdl5kNFf+BOjCQskVtAYVgkhst0W20kT8WHrNBP0qjVxtIAFAUl0bWNwsnyCLNAKfpoO3DecsjhICnWy+B2CbspwA7gWRbOmd1+G1As1cGBDN/P05LoptgnBruEoSH0A7gKVACzgNFAvsgYebcROAN8BTYDnR22ihWLXxVilYpRTLf75mlHy+PbAYr+zUB5oouy7Ah9o0pCkaL/F5lmpUwZ1+MiJFKi9GGll5FLSiPLIyRSrvThfDoDBT5K8eoIiRxT+vAL6OlmYKnSwGdZkFFhPPBT6Uupm4H9SmWT56PGSaUve92Ua5XK02Igskzpy1k35afKuMyNgchYJRFT0KbgvULRfBMHhiiJvHNTblUomm86xUBkoiMKPor8cfjT4qZsZ4rZUu+MAPoAA+XZljiIJCNXtoYC6dtUFYOSBjYFn6TxJnAXaJRQeiPPtqwgehz2iIrvScvAzFIKnkjjNUmxWyRPm4p1khw37VGJGjnS11BggmTKRVI575a7MPsIkIKL0rhLqsuDwCngOlAns/FBpnN1xLPRIqPdBDwAbgPngCNyFtrvVaZUKzOFkW8yU2FjncuC9pKdbkbm+jBgpBlYE1KomZJ8j08SRua4GeuuTMFOuSFryXnS0yBfBqMxQL8tXucie504xZxT1soGlM7wW+AEsEFGaiTQK8l2XznHmOvQKikvvgYgYImYkiotSj1SXomcwd8qw65KbihtFMq75iyct5JkYaa015RGsU7apwJfMpAwpNOhJAQy9eKLJyo8DJhcbpcQFyU07J84z4ErwOJMHQDrsyRSrr3duBckLn0gx6MPK4Pc9VOBzwQSLkYSIe4fGwKQSADT/XZ0JI2xT3KxNlgTpx4YFYBITZCO8qTu8tNRZ5/2/di+7PMC8B/09BnLfqG1+yCMP8DDgIdtSOS+nBhDQQ+pNOMmciWKf/F5UmInYiCSAA5FfdExWc4HURGpA2YQE3IlBTc4fvj7xeskfWNrU0zXTSnIkbLldFL54gelorswyz2pAx0gIvwFLXDNiM6zHVAAAAAASUVORK5CYII=";

    return (
        <div>
            <NavigationBar>
                <h2>Who are we?</h2>
                    <div style={{ display: 'flex', flexDirection: 'column', marginLeft:'-50%' }}>
                        <h3>Kritika Parajuli</h3>
                        <p>
                            <img src={linkedInLogo} alt="LinkedIn Logo" style={{ marginRight: '8px' }} />
                            <a href="https://www.linkedin.com/in/kritika-parajuli-517b46248/" target="_blank" rel="noopener noreferrer" style={{color:'pink'}}>
                                Kritika Parajuli
                            </a>
                        </p>
                        <p >
                            <img src={githubLogo} alt="GitHub Logo" style={{ marginRight: '8px' }} />
                            <a href="https://github.com/kparajul" target="_blank" rel="noopener noreferrer" style={{color:'pink'}}>
                                kparajul
                            </a>
                        </p>
                        <h3>Sakshyam Sarki</h3>
                        <p >
                            <img src={linkedInLogo} alt="LinkedIn Logo" style={{ marginRight: '8px' }} />
                            <a href="https://www.linkedin.com/in/sakshyam-sarki-baa47123b/" target="_blank" rel="noopener noreferrer" style={{color:'pink'}}>
                                Sakshyam Sarki
                            </a>
                        </p>
                        <p >
                            <img src={githubLogo} alt="GitHub Logo" style={{ marginRight: '8px' }} />
                            <a href="https://github.com/Sakshyam103" target="_blank" rel="noopener noreferrer" style={{color:'pink'}}>
                                Sakshyam103
                            </a>
                        </p>
                    </div>
                    <div style={{ display: 'flex', flexDirection: 'column', marginRight:'-50%', marginTop:'-32%'}}>
                        <h3>Molly Morano</h3>
                        <p >
                            <img src={linkedInLogo} alt="LinkedIn Logo" style={{ marginRight: '8px' }} />
                            <a href="https://www.linkedin.com/in/molly-morano/" target="_blank" rel="noopener noreferrer" style={{color:'pink'}}>
                                Molly Morano
                            </a>
                        </p>
                        <p >
                            <img src={githubLogo} alt="GitHub Logo" style={{ marginRight: '8px' }} />
                            <a href="https://github.com/mmorano401" target="_blank" rel="noopener noreferrer" style={{color:'pink'}}>
                                mmorano401
                            </a>
                        </p>
                        <h3>Brandon LaPointe</h3>
                        <p >
                            <img src={linkedInLogo} alt="LinkedIn Logo" style={{ marginRight: '8px' }} />
                            <a href="https://www.linkedin.com/in/brandon-lapointe-414427273/" target="_blank" rel="noopener noreferrer" style={{color:'pink'}}>
                                Brandon LaPointe
                            </a>
                        </p>
                        <p >
                            <img src={githubLogo} alt="GitHub Logo" style={{ marginRight: '8px' }} />
                            <a href="https://github.com/Brandomon" target="_blank" rel="noopener noreferrer" style={{color:'pink'}}>
                                Brandomon
                            </a>
                        </p>
                    </div>
                {/*<h4>Details to be posted later:)</h4>*/}
            </NavigationBar>
        </div>
    );
};

export default AboutUs;
